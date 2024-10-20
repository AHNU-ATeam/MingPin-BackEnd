package com.chuanglian.mingpin.controller;

import com.chuanglian.mingpin.domain.LoginForm;
import com.chuanglian.mingpin.pojo.Result;
import com.chuanglian.mingpin.security.Log;
import com.chuanglian.mingpin.service.LoginService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Log("登录账号")
    @PostMapping("/login")
    public Result login(@Valid @ModelAttribute LoginForm loginForm) {
        return loginService.login(loginForm);
    }
}
