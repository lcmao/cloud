package com.cmd.microservice.common.util;

import com.cmd.microservice.common.jackson.BeanSerializerFactoryEx;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * JSON工具类
 * Created by jerry on 2018/1/8.
 */
public final class JSONUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();
    private static ObjectMapper objectMapperEx = new ObjectMapper();

    static {
        objectMapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
        objectMapperEx.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
        // 不确定的属性项上不要失败
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapperEx.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        objectMapperEx.setSerializerFactory(BeanSerializerFactoryEx.instance);
    }

    public static String toJSONString(Object obj) {
        if(obj == null) {
            return null;
        }

        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 转换为json字符串，会添加类名称
    public static String toJSONStringEx(Object obj) {
        if(obj == null) {
            return null;
        }

        try {
            return objectMapperEx.writeValueAsString(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T extends Object> T parseToObject(String source, Class<T> valueType) {
        try {
            return objectMapper.readValue(source, valueType);
        } catch (IOException e) {
            return null;
        }
    }

    private static Object resolveObject(Object obj) throws Exception {
        if(obj == null) return null;
        if(obj instanceof Collection) {
            Collection collection = (Collection)obj;
            Collection newColl = collection.getClass().newInstance();
            for(Object colObj : collection) {
                Object resolveObj = resolveObject(colObj);
                newColl.add(resolveObj);
            }
            return newColl;
        } else if(obj instanceof Map) {
            Map map = (Map)obj;
            Object className = map.get("_class_");
            if(className != null && className instanceof String) {
                Class toClass = Class.forName((String)className);
                return ObjectUtil.mapToObject(map, toClass, true);
            }
            return obj;
        } else {
            // 枚举所有属性
            Class objClass = obj.getClass();
            Field[] fields = objClass.getDeclaredFields();
            for (Field field : fields) {
                int mod = field.getModifiers();
                if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                    continue;
                }
                // 处理 Collections 和 Map，其它不处理
                field.setAccessible(true);
                Object value = field.get(obj);
                if(value != null && (value instanceof  Collection || value instanceof Map)) {
                    Object proObj = resolveObject(value);
                    try {
                        field.set(obj, proObj);
                    } catch (Exception ex) {
                        // 如果proObj是字符串，obj不是字符，那么进行对象转换，使用json来转换
                        if(proObj instanceof String) {
                            field.set(obj, JSONUtil.parseToObject((String)proObj, field.getDeclaringClass()));
                        }else {
                            throw ex;
                        }
                    }
                }
            }
        }
        return obj;
    }

    // 根据类名进行实例化
    public static <T extends Object> T parseToObjectEx(String source, Class<T> valueType) {
        try {
            T obj =  objectMapper.readValue(source, valueType);
            return (T)resolveObject(obj);
        } catch (Exception e) {
            return null;
        }
    }

    public static <T extends Object> T parseToObject(String source, TypeReference valueType) {
        try {
            return objectMapper.readValue(source, valueType);
        } catch (IOException e) {
            return null;
        }
    }


}
