package com.chuanglian.mingpin.mapper.user;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chuanglian.mingpin.entity.user.Teacher;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
@TableName("userManagement.[teacher]")
public interface TeacherMapper extends BaseMapper<Teacher> {
    @Select("select * from mingpin.userManagement.teacher")
    List<Teacher> list();


    @Insert("insert into userManagement.teacher(name, phone, permission_status, position, campus_id) values (#{name},#{phone},#{permissionStatus},#{position},#{campusId})")
    void add(Teacher teacher);

    @Delete("delete from userManagement.teacher where teacher_id = #{id}")
    void delete(Integer id);

    void update(Teacher teacher);

    @Select("select *from userManagement.teacher where teacher_id = #{teacherId}")
    Teacher selectTeacherById(@Param("teacherId") Long teacherId);
}

