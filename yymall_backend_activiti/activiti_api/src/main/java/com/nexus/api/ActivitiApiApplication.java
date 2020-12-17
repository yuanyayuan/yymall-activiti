package com.nexus.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.nexus"})
@SpringBootApplication
public class ActivitiApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActivitiApiApplication.class, args);
    }

}
