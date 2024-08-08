package com.chuanglian.mingpin.controller;

import com.chuanglian.mingpin.entity.User;
import com.chuanglian.mingpin.pojo.Result;
import com.chuanglian.mingpin.service.RegisterService;
import com.chuanglian.mingpin.utils.Role;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @PostMapping("/register")
    public Result register(String type,
                           @Pattern(regexp = "^\\S{11}$") String phone,
                           @Pattern(regexp = "^\\S{6,25}$") String password) {
        System.out.println(type + phone + password);

        // 查询用户是否重复
        User user = registerService.checkUserExists(phone);
        if (user == null) {
            // 没有被占用，执行注册
            Role role = Role.fromType(type);
            registerService.register(role, phone, password);
            return Result.success();
        } else {
            // 已经被占用
            return Result.error("用户名重复");
        }
    }
}
