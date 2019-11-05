package com.cmd.microservice.registerserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class RegisterserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegisterserverApplication.class, args);
	}

}

