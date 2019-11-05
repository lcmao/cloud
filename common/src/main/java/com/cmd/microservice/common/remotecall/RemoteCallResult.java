package com.cmd.microservice.common.remotecall;

import lombok.Data;

@Data
public class RemoteCallResult {
    private String className;
    private String methodName;
    private String result;
}
