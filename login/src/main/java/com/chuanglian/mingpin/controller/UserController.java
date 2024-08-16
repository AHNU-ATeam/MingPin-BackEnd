package com.chuanglian.mingpin.controller;

import com.chuanglian.mingpin.pojo.Result;
import com.chuanglian.mingpin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/test")
    public Result test() {
        return Result.success("测试成功");
    }

    @RequestMapping("/logout")
    public Result logout() {
        return userService.logout();
    }
}
