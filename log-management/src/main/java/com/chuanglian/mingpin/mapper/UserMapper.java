package com.chuanglian.mingpin.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chuanglian.mingpin.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@TableName("userManagement.[user]")
@Repository("logUserMapper")
public interface UserMapper extends BaseMapper<User> {

}
