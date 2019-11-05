package com.cmd.microservice.common.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class ObjectUtil {
    /**
     * 使用reflect进行转换,map集合转javabean
     *
     * @param map          要转换的源数据map
     * @param beanClass   要转换成的类
     * @param checkType   是否检查类型，true：字段类型不一样不会设置不会异常，false：字段类型不一样抛出异常
     * @return  转换后对象
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static <T> T mapToObject(Map<String, Object> map, Class<T> beanClass, boolean checkType) throws IllegalAccessException, InstantiationException {
        if (map == null){
            return null;
        }

        T obj = beanClass.newInstance();

        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            int mod = field.getModifiers();
            if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                continue;
            }

            field.setAccessible(true);
            Object value = map.get(field.getName());
            if(checkType) {
                if(value == null || value.getClass() == field.getType()) {
                    field.set(obj, value);
                }
            } else {
                field.set(obj, value);
            }
        }

        return obj;
    }

    /**
     * 使用reflect进行转换,javabean对象转map集合
     *
     * @param obj
     * @return map集合
     * @throws IllegalAccessException java.lang.IllegalAccessException
     * @throws InstantiationException java.lang.InstantiationException
     */
    public static Map<String, Object> objectToMap(Object obj) throws Exception {
        if (obj == null) {
            return null;
        }

        Map<String, Object> map = new HashMap<>();

        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            map.put(field.getName(), field.get(obj));
        }

        return map;
    }
    /**
     * 使用Introspector，map集合成javabean
     *
     * @param map       map
     * @param beanClass bean的Class类
     * @param checkType   是否检查类型，true：字段类型不一样不会设置不会异常，false：字段类型不一样抛出异常
     * @return bean对象
     */
    public static <T> T mapToBean(Map<String, Object> map, Class<T> beanClass, boolean checkType) {
        try {
            T t = beanClass.newInstance();
            BeanInfo beanInfo = Introspector.getBeanInfo(t.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                Method setter = property.getWriteMethod();
                if (setter != null) {
                    Object value = map.get(property.getName());
                    if(checkType) {
                        if(value == null || value.getClass() == property.getPropertyType()) {
                            setter.invoke(t, value);
                        }
                    } else {
                        setter.invoke(t, value);
                    }
                }
            }
            return t;
        } catch (Exception ex) {
            //log.error("########map集合转javabean出错######，错误信息，{}", ex.getMessage());
            throw new RuntimeException(ex);
        }

    }

    /**
     * 使用Introspector，对象转换为map集合
     *
     * @param beanObj javabean对象
     * @return map集合
     */
    public static Map<String, Object> beanToMap(Object beanObj) {

        if (null == beanObj) {
            return null;
        }

        Map<String, Object> map = new HashMap<>();

        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(beanObj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                if (key.compareToIgnoreCase("class") == 0) {
                    continue;
                }
                Method getter = property.getReadMethod();
                Object value = getter != null ? getter.invoke(beanObj) : null;
                map.put(key, value);
            }

            return map;
        } catch (Exception ex) {
            //log.error("########javabean集合转map出错######，错误信息，{}", ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
}
