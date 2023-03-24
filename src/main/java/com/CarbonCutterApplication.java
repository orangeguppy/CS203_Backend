package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class CarbonCutterApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(CarbonCutterApplication.class, args);
    }


    public void run(String... args) throws Exception {
    }
}