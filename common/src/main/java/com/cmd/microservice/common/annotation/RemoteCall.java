package com.cmd.microservice.common.annotation;

import java.lang.annotation.*;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Retention(RUNTIME)
@Target(ElementType.TYPE)
public @interface RemoteCall {
    /**
     *
     * @return  服务所运行的服务器名称
     */
    String runServerName();
}
