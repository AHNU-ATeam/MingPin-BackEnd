package com.pingmin;

import org.springframework.boot.SpringApplication;

public class TestBackEndApplication {

    public static void main(String[] args) {
        SpringApplication.from(Application::main).with(TestcontainersConfiguration.class).run(args);
    }

}
