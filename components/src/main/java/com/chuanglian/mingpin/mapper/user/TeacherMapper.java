package com.chuanglian.mingpin.mapper.user;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chuanglian.mingpin.entity.user.Teacher;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
@TableName("userManagement.[teacher]")
public interface TeacherMapper extends BaseMapper<Teacher> {
//    @Select("select * from userManagement.teacher where is_deleted = 0")
//    List<Teacher> list();


//    @Update("UPDATE userManagement.teacher\n" +
//            "SET is_deleted = 1\n" +
//            "WHERE teacher_id = #{id}")
//    int delete(Integer id);

//    @Update("UPDATE userManagement.teacher set  avatar_address = #{avatarAddress},sex = #{sex},name = #{name},identification_number = #{identificationNumber},phone = #{phone}, permission_status = #{permissionStatus},position = #{position}, password = #{password},campus_id = #{campusId},updated_at = #{updatedAt} where teacher_id = #{teacherId}")
//    int update(Teacher teacher);

    @Select("SELECT user_id FROM userManagement.teacher WHERE teacher_id = #{teacherId}")
    Integer getUserIdByTeacherId(Integer teacherId);

    @Select("select * from userManagement.teacher where teacher_id = #{teacherId}")
    Teacher selectTeacherById(@Param("teacherId") Integer teacherId);
}

