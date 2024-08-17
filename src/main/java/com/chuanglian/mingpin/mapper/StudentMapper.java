package com.chuanglian.mingpin.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chuanglian.mingpin.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@TableName("[userManagement].[student]")
public interface StudentMapper extends BaseMapper<Student> {

    @Select("select * from userManagement.student where campus_id = #{campusId}")
    List<Student> selectList(Integer campusId);
}
