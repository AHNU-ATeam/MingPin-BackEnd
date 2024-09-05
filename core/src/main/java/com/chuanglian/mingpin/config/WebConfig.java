package com.chuanglian.mingpin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //将所有/swagger-ui-view/** 访问都映射到classpath:/swagger-ui/ 目录下
        registry.addResourceHandler("/swagger-ui/**").addResourceLocations("classpath:/swagger-ui");
    }

}