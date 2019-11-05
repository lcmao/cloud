package com.cmd.microservice.common;

import com.cmd.microservice.common.util.JSONUtil;
import org.aspectj.lang.annotation.DeclareWarning;

import java.util.ArrayList;
import java.util.List;

public class Test {

    public static class ClassB {
        public String user;
        public Integer age;
    }

    public static class ClassA<T> {
        public String name;
        public List<T> ls;
    }


    public static void main(String[] argv) {
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

        String json = JSONUtil.toJSONStringEx(classA);
        System.out.println(json);

        ClassA<ClassB> obj = JSONUtil.parseToObjectEx(json, ClassA.class);
        System.out.println(obj);


        json = JSONUtil.toJSONString(classA);
        System.out.println(json);

        obj = JSONUtil.parseToObject(json, ClassA.class);
        System.out.println(obj);
    }
}
