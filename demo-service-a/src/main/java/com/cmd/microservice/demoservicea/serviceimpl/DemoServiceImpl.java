package com.cmd.microservice.demoservicea.serviceimpl;

import com.cmd.microservice.service.DemoService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
public class DemoServiceImpl implements DemoService {
    @Override
    public void hello(String name) {
        System.out.println("hello " + name);
    }

    @Override
    public Date getDate() {
        return new Date();
    }

    @Override
    public int updateUser(int id, String userName, Integer age) {
        System.out.println("id=" + id + ",userName=" + userName + ",age=" + age);
        return 1;
    }

    @Override
    public ClassA getClassA() {
        ClassA<ClassB> classA = new ClassA<>();
        classA.name = "test";
        classA.ls = new ArrayList<>();
        ClassB b1 = new ClassB();
        b1.user = "b1";
        b1.age = 10;
        classA.ls.add(b1);
        ClassB b2 = new ClassB();
        b2.user = "b2";
        b2.age = 20;
        classA.ls.add(b2);
        return classA;
    }
}
