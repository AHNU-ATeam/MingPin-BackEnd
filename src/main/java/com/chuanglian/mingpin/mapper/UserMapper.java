package com.chuanglian.mingpin.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chuanglian.mingpin.entity.Principal;
import com.chuanglian.mingpin.entity.Student;
import com.chuanglian.mingpin.entity.Teacher;
import com.chuanglian.mingpin.entity.User;
import com.chuanglian.mingpin.utils.Role;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

@TableName("[userManagement].[user]")
public interface UserMapper extends BaseMapper<User> {

}
