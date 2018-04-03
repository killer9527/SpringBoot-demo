package com.wfw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.support.SpringFactoriesLoader;

@SpringBootApplication
public class SpringBootDemoSingleApplication {

    public static void main(String[] args) {
        //SpringApplication.run(SpringBootDemoSingleApplication.class, args);
         SpringApplication application = new SpringApplication(SpringBootDemoSingleApplication.class);
         application.run(args);
    }
}
