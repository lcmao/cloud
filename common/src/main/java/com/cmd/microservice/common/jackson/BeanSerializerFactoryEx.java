package com.cmd.microservice.common.jackson;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.cfg.SerializerFactoryConfig;
import com.fasterxml.jackson.databind.ser.BeanSerializerBuilder;
import com.fasterxml.jackson.databind.ser.BeanSerializerFactory;

public class BeanSerializerFactoryEx extends BeanSerializerFactory {

    public static final BeanSerializerFactory instance = new BeanSerializerFactoryEx((SerializerFactoryConfig)null);

    protected BeanSerializerFactoryEx(SerializerFactoryConfig config) {
        super(config);
    }

    protected BeanSerializerBuilder constructBeanSerializerBuilder(BeanDescription beanDesc) {
        return new BeanSerializerBuilderEx(beanDesc);
    }
}
