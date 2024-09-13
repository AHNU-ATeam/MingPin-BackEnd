package com.chuanglian.mingpin.service;

import com.chuanglian.mingpin.domain.LoginForm;
import com.chuanglian.mingpin.pojo.Result;

public interface LoginService {
    // 登录服务
    Result login(LoginForm loginForm);
}
