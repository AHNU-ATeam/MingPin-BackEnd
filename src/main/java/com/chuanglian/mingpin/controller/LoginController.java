package com.chuanglian.mingpin.controller;

import com.chuanglian.mingpin.domain.LoginUser;
import com.chuanglian.mingpin.pojo.Result;
import com.chuanglian.mingpin.service.LoginService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public Result login(@Valid @ModelAttribute LoginUser loginUser) {
        return loginService.login(loginUser);
    }
}
