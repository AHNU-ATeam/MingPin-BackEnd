package com.chuanglian.mingpin.service;

import com.chuanglian.mingpin.entity.user.User;
import com.chuanglian.mingpin.pojo.Result;
import com.chuanglian.mingpin.utils.RoleEnum;

public interface RegisterService {

    // 根据用户名查询
    User checkUserExists(String phone);

    // 注册服务
    Result register(RoleEnum role, String phone, String password);

}
