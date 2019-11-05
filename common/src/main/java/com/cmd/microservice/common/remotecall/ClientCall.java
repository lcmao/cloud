package com.cmd.microservice.common.remotecall;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

// 这个类是用来获取远程连接对象的，用来简化操作而已
@Component
public class ClientCall {

    @Bean
    @LoadBalanced
    RestTemplate restTemplate(){
        return  new RestTemplate();
    }

    @Autowired
    RestTemplate restTemplate;

    static RestTemplate staticRestTemplate;

    public ClientCall() {
        System.out.println("ClientCall construct");
    }

    @PostConstruct
    public void init() {
        System.out.println("ClientCall init");
        staticRestTemplate = this.restTemplate;
    }
}
