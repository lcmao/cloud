package com.cmd.microservice.common.jackson;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanSerializer;
import com.fasterxml.jackson.databind.ser.BeanSerializerBuilder;


public class BeanSerializerBuilderEx extends BeanSerializerBuilder {

    public BeanSerializerBuilderEx(BeanDescription beanDesc) {
        super(beanDesc);
    }

    public JsonSerializer<?> build() {
        BeanSerializer beanSerializer = (BeanSerializer)super.build();
        // 使用父类来生成BeanSerializer，然后用属性进行覆盖，减少代码复制
        // return new BeanSerializer(this._beanDesc.getType(), this, properties, this._filteredProperties);
        BeanSerializerEx beanSerializerEx = new BeanSerializerEx(beanSerializer);
        return beanSerializerEx;
    }
}
