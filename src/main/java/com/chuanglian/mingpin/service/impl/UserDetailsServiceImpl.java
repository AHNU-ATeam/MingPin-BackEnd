package com.chuanglian.mingpin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chuanglian.mingpin.domain.LoginUser;
import com.chuanglian.mingpin.domain.UserDetail;
import com.chuanglian.mingpin.entity.User;
import com.chuanglian.mingpin.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

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
        // TODO 查询对应的权限信息

        // 用户数据封装成UserDetails返回
        LoginUser loginUser = new LoginUser(user.getId(),
                user.getBoundPhone(), user.getPassword());
        return new UserDetail(loginUser);
    }
}
