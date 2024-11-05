package com.chuanglian.mingpin.service;

import com.chuanglian.mingpin.pojo.Result;
import com.chuanglian.mingpin.pojo.UserChangePasswordDTO;

public interface UserService {

    // 注销服务
    Result logout();

    Result changePassword(UserChangePasswordDTO userChangePasswordDTO);
}
