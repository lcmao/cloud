package com.cmd.microservice.common.remotecall;

import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;

public class RemoteCallFactoryBean<T> implements FactoryBean<T> {

    private Class<T> remoteCallInterface;

    public RemoteCallFactoryBean(Class<T> inf) {
        this.remoteCallInterface = inf;
    }

    @Override
    public T getObject() throws Exception {
        RemoteCallProxy proxy = new RemoteCallProxy<T>(this.remoteCallInterface);
        return (T)Proxy.newProxyInstance(remoteCallInterface.getClassLoader(), new Class[] { remoteCallInterface }, proxy);
    }

    @Override
    public Class<?> getObjectType() {
        return this.remoteCallInterface;
    }
}
