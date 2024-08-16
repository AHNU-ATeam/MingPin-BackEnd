package com.chuanglian.mingpin.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chuanglian.mingpin.entity.Student;

@TableName("[userManagement].[student]")
public interface StudentMapper extends BaseMapper<Student> {

}
