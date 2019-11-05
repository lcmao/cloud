package com.cmd.microservice.demoservicea;

import com.cmd.microservice.service.DemoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoServiceBApplicationTests {
    @Autowired
    DemoService demoService;

	@Test
	public void contextLoads() {

	}

	@Test
	public void test() {
	    demoService.hello("name");

        Date current = demoService.getDate();
        System.out.println("time:" + current);
    }
}

