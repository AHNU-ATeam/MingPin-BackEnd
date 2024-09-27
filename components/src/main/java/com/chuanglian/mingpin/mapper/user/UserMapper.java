package com.chuanglian.mingpin.mapper.user;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chuanglian.mingpin.entity.user.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@TableName("userManagement.[user]")
public interface UserMapper extends BaseMapper<User> {

}
