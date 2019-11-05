package com.cmd.microservice.common.remotecall;

import com.cmd.microservice.common.util.JSONUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.HashMap;

@RestController
@RequestMapping("/remote-call")
public class RemoteCallController implements ApplicationContextAware {
    private Log log = LogFactory.getLog(this.getClass());

    private ApplicationContext applicationContext;
    private HashMap<String, Class> nativeClass;

    public RemoteCallController() {
        log.info("RemoteCallController construct");
        nativeClass = new HashMap<String, Class>();
        nativeClass.put("byte", byte.class);
        nativeClass.put("short", short.class);
        nativeClass.put("int", int.class);
        nativeClass.put("long", long.class);
        nativeClass.put("char", char.class);
        nativeClass.put("float", float.class);
        nativeClass.put("double", double.class);
        nativeClass.put("boolean", boolean.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @PostMapping("invoke")
    @ResponseBody
    public RemoteCallResult remoteCall(@RequestBody RemoteCallObject remoteCallObject, HttpServletResponse response) throws Exception {
        if(log.isDebugEnabled()) {
            log.debug("remoteCall:" + remoteCallObject);
        }
        RemoteCallResult remoteCallResult = new RemoteCallResult();
        remoteCallResult.setClassName(remoteCallObject.getClassName());
        remoteCallObject.setMethodName(remoteCallObject.getMethodName());
        //System.out.println(remoteCallObject);
        Class theClass = Class.forName(remoteCallObject.getClassName());
        Object bean = this.applicationContext.getBean(theClass);
        Method method = null;
        if(remoteCallObject.getArgTypes() == null) {
            // 没填写参数，匹配名字就可以
            Method[] methods = theClass.getDeclaredMethods();
            for(Method m : methods) {
                if(m.getName().equals(remoteCallObject.getMethodName())) {
                    method = m;
                    break;
                }
            }
            if(method == null) {
                throw new RuntimeException("not find method of " + remoteCallObject);
            }
        } else {
            Class[] argTypes = new Class[remoteCallObject.getArgTypes().size()];
            for(int i = 0; i < remoteCallObject.getArgTypes().size(); i++) {
                // 如果是基本类型，那么直接返回
                argTypes[i] = this.nativeClass.get(remoteCallObject.getArgTypes().get(i));
                if(argTypes[i] == null) {
                    argTypes[i] = Class.forName(remoteCallObject.getArgTypes().get(i));
                }
            }
            method = theClass.getMethod(remoteCallObject.getMethodName(), argTypes);
        }
        Class[] paraTypes = method.getParameterTypes();
        Object result;
        if(paraTypes == null || paraTypes.length == 0) {
            result = method.invoke(bean);
        } else {
            // 具有参数
            Object[] paras = new Object[paraTypes.length];
            for (int i = 0; i < paras.length; i++) {
                paras[i] = jsonToObject(remoteCallObject.getArgs().get(i), paraTypes[i]);
            }
            result = method.invoke(bean, paras);
        }
        if(result == null) return remoteCallResult;
        String str = JSONUtil.toJSONStringEx(result);
        remoteCallResult.setResult(str);
        return remoteCallResult;
        /*response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(str);*/
    }

    private Object jsonToObject(String json, Class type) {
        return JSONUtil.parseToObjectEx(json, type);
    }

    @RequestMapping("")
    public  String hello()
    {
        return  "hello world";
    }

    static class User {
        public int id;
        public String name;

        public int getId() {
            return id;
        }
    }

    @RequestMapping("/user")
    @ResponseBody
    public  User getUser()
    {
        User user = new User();
        user.id = 100;
        user.name = "user name";
        return  user;
    }
}
