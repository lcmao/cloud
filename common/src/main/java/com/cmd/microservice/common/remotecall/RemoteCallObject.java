package com.cmd.microservice.common.remotecall;

import lombok.Data;

import java.util.ArrayList;

@Data
public class RemoteCallObject {
    private String className;
    private String methodName;
    private ArrayList<String> argTypes;
    private ArrayList<String> args;
}
