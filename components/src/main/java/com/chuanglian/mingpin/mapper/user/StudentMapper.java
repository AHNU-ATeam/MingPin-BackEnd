package com.chuanglian.mingpin.mapper.user;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chuanglian.mingpin.entity.user.Student;
import org.apache.ibatis.annotations.Mapper;

@TableName("[userManagement].[student]")
@Mapper
public interface StudentMapper extends BaseMapper<Student> {

}

