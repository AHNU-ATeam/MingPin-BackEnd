package com.chuanglian.mingpin.controller;

import com.chuanglian.mingpin.domain.RegisterForm;
import com.chuanglian.mingpin.entity.user.User;
import com.chuanglian.mingpin.pojo.Result;
import com.chuanglian.mingpin.service.RegisterService;
import com.chuanglian.mingpin.utils.RoleEnum;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @PostMapping("/register")
    public Result register(@Valid @ModelAttribute RegisterForm registerForm) {
        // 从请求体中提取信息
        String type = registerForm.getRole();
        String phone = registerForm.getPhone();
        String password = registerForm.getPassword();

        // 查询用户是否重复
        User user = registerService.checkUserExists(phone);
        if (user == null) {
            // 没有被占用，执行注册
            RoleEnum role = RoleEnum.fromType(type);
            registerService.register(role, phone, password);
            return Result.success();
        } else {
            // 已经被占用
            return Result.error("用户名重复");
        }
    }
}
