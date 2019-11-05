package com.cmd.microservice.common.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanSerializer;

import java.io.IOException;

public class BeanSerializerEx extends BeanSerializer {

    public BeanSerializerEx(BeanSerializer beanSerializer) {
        super(beanSerializer);
    }

    protected void serializeFields(Object bean, JsonGenerator gen, SerializerProvider provider) throws IOException {
        // 先把className写进入
        String className = bean.getClass().getName();
        if(!className.startsWith("java.")) {
            gen.writeFieldName("_class_");
            gen.writeString(className);
        }
        super.serializeFields(bean, gen, provider);
    }
}
