package com.zyonicsoftware.haproxyapi.api;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication
public class SpringApplication {

    public void startSpring(final int connectionPort) {
        final org.springframework.boot.SpringApplication application = new org.springframework.boot.SpringApplication(SpringApplication.class);
        application.setDefaultProperties(Collections.singletonMap("server.port", connectionPort));
        application.run();
    }

}
