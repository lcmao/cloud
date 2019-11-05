package com.cmd.microservice.service;

import com.cmd.microservice.common.annotation.RemoteCall;

import java.util.Date;
import java.util.List;

@RemoteCall(runServerName = "demo-service-a")
public interface DemoService {
    public static class ClassB {
        public String user;
        public Integer age;
    }

    public static class ClassA<T> {
        public String name;
        public List<T> ls;
    }

    void hello(String name);

    // 获取当前时间
    Date getDate();

    int updateUser(int id, String userName, Integer age);

    ClassA getClassA();
}
