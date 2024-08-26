package com.chuanglian.mingpin.controller;

import com.chuanglian.mingpin.pojo.Result;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @RequestMapping("/hello")
    @PreAuthorize("hasAuthority('sys:core:test')")
    public Result test() {
        return Result.success("域外登录测试成功");
    }

}
