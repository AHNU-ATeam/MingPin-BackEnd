package com.chuanglian.mingpin.controller;

import com.chuanglian.mingpin.pojo.Result;
import com.chuanglian.mingpin.pojo.UserChangePasswordDTO;
import com.chuanglian.mingpin.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/test")
    @PreAuthorize("hasAuthority('sys:core:test')")
    public Result test() {
        return Result.success("用户登录令牌测试成功");
    }

    @RequestMapping("/logout")
    @PreAuthorize("hasAuthority('sys:user:logout')")
    public Result logout() {
        return userService.logout();
    }

    @PostMapping("/change-password")
    public Result changePassword(@RequestBody UserChangePasswordDTO userChangePasswordDTO) {
        return userService.changePassword(userChangePasswordDTO);
    }
}
