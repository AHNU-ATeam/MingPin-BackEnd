package com.chuanglian.mingpin.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @RequestMapping("/hello")
    @PreAuthorize("hasAuthority('test')")
    public String test() {
        return "Test";
    }

}
