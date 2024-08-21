package com.chuanglian.mingpin.mapper.permission;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chuanglian.mingpin.entity.permission.UserRole;

@TableName("permissionManagement.user_role")
public interface UserRoleMapper extends BaseMapper<UserRole> {
}
