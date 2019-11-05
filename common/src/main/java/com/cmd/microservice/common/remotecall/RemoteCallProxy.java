package com.cmd.microservice.common.remotecall;

import com.cmd.microservice.common.annotation.RemoteCall;
import com.cmd.microservice.common.util.JSONUtil;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.boot.jackson.JsonObjectSerializer;
import org.springframework.boot.json.JsonParser;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

public class RemoteCallProxy<T> implements InvocationHandler, Serializable {
    private static final long serialVersionUID = -6424540398255529838L;

    private Class<T> remoteInterface;
    private String serverUrl;


    public RemoteCallProxy(Class<T> remoteInterface) {
        this.remoteInterface = remoteInterface;
        RemoteCall remoteCall = remoteInterface.getAnnotation(RemoteCall.class);
        serverUrl = "http://" + remoteCall.runServerName() + "/remote-call/invoke";
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RemoteCallObject callObject = new RemoteCallObject();
        callObject.setClassName(method.getDeclaringClass().getName());
        callObject.setMethodName(method.getName());
        // 参数
        Class[] paraTypes = method.getParameterTypes();
        if(paraTypes != null) {
            ArrayList<String> types = new ArrayList<String>(paraTypes.length);
            for(int i = 0; i < paraTypes.length; i++) {
                types.add(paraTypes[i].getName());
            }
            callObject.setArgTypes(types);
        }
        if(args != null && args.length > 0) {
            ArrayList<String> remoteCallArgs = new ArrayList<String>(args.length);
            for(Object obj : args) {
                remoteCallArgs.add(JSONUtil.toJSONStringEx(obj));
            }
            callObject.setArgs(remoteCallArgs);
        }
        //Object result = ClientCall.staticRestTemplate.getForEntity(this.serverUrl, method.getReturnType(), callObject).getBody();
        //HashMap<String, RemoteCallObject> map = new HashMap<String, RemoteCallObject>();
        //map.put("remoteCallObject", callObject);
        //Object result = ClientCall.staticRestTemplate.postForEntity(this.serverUrl, callObject, method.getReturnType()).getBody();
        RemoteCallResult result = ClientCall.staticRestTemplate.postForEntity(this.serverUrl, callObject, RemoteCallResult.class).getBody();
        if(result.getResult() == null) return null;
        Object retObj = JSONUtil.parseToObjectEx(result.getResult(), method.getReturnType());
        return retObj;
    }
}
