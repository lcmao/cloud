package com.cmd.microservice.demoservicea.controller;

import com.cmd.microservice.common.remotecall.RemoteCallObject;
import com.cmd.microservice.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private DemoService demoService;

    public TestController() {
        System.out.println("TestController start");
    }

    @GetMapping("")
    @ResponseBody
    public Object index() {
        demoService.hello("abc");
        System.out.println("now: " + demoService.getDate());
        int count = demoService.updateUser(100, "test_user", 10);
        System.out.println("count=" + count);

        DemoService.ClassA classA = demoService.getClassA();
        System.out.println(classA);
        return "Success";
    }
}
