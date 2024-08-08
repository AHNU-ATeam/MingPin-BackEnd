package com.chuanglian.mingpin.service;

import com.chuanglian.mingpin.entity.User;
import com.chuanglian.mingpin.pojo.Result;

public interface LoginService {
    // 登录服务
    Result login(User user);
}
