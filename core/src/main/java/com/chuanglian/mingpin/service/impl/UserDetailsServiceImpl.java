package com.chuanglian.mingpin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chuanglian.mingpin.domain.LoginForm;
import com.chuanglian.mingpin.domain.UserForm;
import com.chuanglian.mingpin.entity.user.User;
import com.chuanglian.mingpin.mapper.permission.MenuMapper;
import com.chuanglian.mingpin.mapper.user.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 查询用户信息
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getBoundPhone, username);
        User user = userMapper.selectOne(queryWrapper);
        // 安全性校验
        if (Objects.isNull(user)) {
            throw new RuntimeException("该用户不存在");
        }

        // 查询对应的权限信息
        List<String> permissions = menuMapper.selectPermsByUserID(user.getId());

        // 用户数据封装成UserDetails返回
        LoginForm loginForm = new LoginForm(user.getId(),
                user.getBoundPhone(), user.getPassword());
        return new UserForm(loginForm, permissions);
    }
}
