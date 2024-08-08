package com.chuanglian.mingpin.service;

import com.chuanglian.mingpin.entity.User;
import com.chuanglian.mingpin.pojo.Result;
import com.chuanglian.mingpin.utils.Role;

public interface RegisterService {

    // 根据用户名查询
    User checkUserExists(String phone);

    // 注册服务
    Result register(Role role, String phone, String password);

}
