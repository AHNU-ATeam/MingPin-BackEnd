package com.chuanglian.mingpin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.chuanglian.mingpin.mapper")
public class MingPinApplication {

    public static void main(String[] args) {
        SpringApplication.run(MingPinApplication.class, args);
        System.out.println("YES");
        System.out.println("Test");
    }
}
