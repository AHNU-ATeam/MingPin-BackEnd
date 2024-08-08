package com.chuanglian.mingpin.controller;

import com.chuanglian.mingpin.entity.User;
import com.chuanglian.mingpin.pojo.Result;
import com.chuanglian.mingpin.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        return loginService.login(user);
    }
}
