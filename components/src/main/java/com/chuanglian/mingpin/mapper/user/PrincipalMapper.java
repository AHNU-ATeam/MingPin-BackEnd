package com.chuanglian.mingpin.mapper.user;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chuanglian.mingpin.entity.user.Principal;

@TableName("[userManagement].[principal]")
public interface PrincipalMapper extends BaseMapper<Principal> {

}
