package com.mingpin.service.impl;

import com.mingpin.entity.User;
import com.mingpin.mapper.UserMapper;
import com.mingpin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByID(Integer id) {
        return userMapper.findByID(id);
    }
}
