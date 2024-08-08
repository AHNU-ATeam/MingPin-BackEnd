package com.chuanglian.mingpin.controller;

import com.chuanglian.mingpin.entity.User;
import com.chuanglian.mingpin.pojo.Result;
import com.chuanglian.mingpin.service.UserService;
import com.chuanglian.mingpin.utils.Role;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    /**
    用户注册
    需要type phone password
     */



}
