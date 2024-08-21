package com.chuanglian.mingpin.service.impl;

import com.chuanglian.mingpin.domain.UserForm;
import com.chuanglian.mingpin.pojo.Result;
import com.chuanglian.mingpin.service.UserService;
import com.chuanglian.mingpin.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RedisCache redisCache;

    @Override
    public Result logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserForm userForm = (UserForm) authentication.getPrincipal();
        Integer userId = userForm.getLoginForm().getId();
        redisCache.deleteObject("login:" + userId);
        return Result.success("退出成功");
    }
}
