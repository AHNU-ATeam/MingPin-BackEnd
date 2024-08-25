package com.chuanglian.mingpin.mapper.user;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chuanglian.mingpin.entity.user.Student;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@TableName("[userManagement].[student]")
public interface StudentMapper extends BaseMapper<Student> {

    @Select("select * from userManagement.student where campus_id = #{campusId}")
    List<Student> selectCampusList(Integer campusId);

    @Select("select * from userManagement.student where class_id = #{classId}")
    List<Student> selectClassList(Integer classId);
}

