package com.cmd.microservice.common.annotation;

import com.cmd.microservice.common.remotecall.RemoteCallScannerRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(RemoteCallScannerRegistrar.class)
public @interface RemoteCallScan {
    /**
     * 扫描的包
     * @return  包名数组
     */
    String[] value() default {};

    /**
     *
     * @return  本地服务名称
     */
    String localServerName();
}
