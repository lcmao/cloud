package com.cmd.microservice.demoservicea;

import com.cmd.microservice.common.annotation.RemoteCallScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(value = {"com.cmd.microservice.common.remotecall", "com.cmd.microservice.demoservicea.controller"})
@RemoteCallScan(value = {"com.cmd.microservice.service"}, localServerName = "demo-service-b")
public class DemoServiceBApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoServiceBApplication.class, args);
	}

}

