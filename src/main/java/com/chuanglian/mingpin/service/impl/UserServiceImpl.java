package com.chuanglian.mingpin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chuanglian.mingpin.domain.LoginUser;
import com.chuanglian.mingpin.entity.Principal;
import com.chuanglian.mingpin.entity.Student;
import com.chuanglian.mingpin.entity.Teacher;
import com.chuanglian.mingpin.entity.User;
import com.chuanglian.mingpin.mapper.PrincipalMapper;
import com.chuanglian.mingpin.mapper.StudentMapper;
import com.chuanglian.mingpin.mapper.TeacherMapper;
import com.chuanglian.mingpin.mapper.UserMapper;
import com.chuanglian.mingpin.pojo.Result;
import com.chuanglian.mingpin.service.UserService;
import com.chuanglian.mingpin.utils.RedisCache;
import com.chuanglian.mingpin.utils.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RedisCache redisCache;

    @Override
    public Result logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Integer userId = loginUser.getUser().getId();
        System.out.println("login:" + userId);
        redisCache.deleteObject("login:" + userId);
        return Result.success("退出成功");
    }
}
