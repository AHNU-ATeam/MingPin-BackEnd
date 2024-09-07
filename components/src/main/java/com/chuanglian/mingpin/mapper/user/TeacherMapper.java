package com.chuanglian.mingpin.mapper.user;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chuanglian.mingpin.entity.user.Teacher;
import com.chuanglian.mingpin.entity.user.vo.TeacherVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
@TableName("userManagement.[teacher]")
public interface TeacherMapper extends BaseMapper<Teacher> {
    @Select("select * from userManagement.teacher")
    List<Teacher> list();


    @Insert("insert into userManagement.teacher(name, phone, permission_status, position,created_at,updated_at, avatar_address, sex, Identification_number, password) values (#{name},#{phone},#{permissionStatus},#{position},#{createdAt},#{updatedAt},#{avatarAddress},#{sex},#{IdentificationNumber},#{password})")
    int add(Teacher teacher);

    @Update("UPDATE userManagement.teacher\n" +
            "SET is_deleted = 1\n" +
            "WHERE teacher_id = #{id}")
    int delete(Integer id);

    @Update("UPDATE userManagement.teacher set  avatar_address = #{avatarAddress},sex = #{sex},name = #{name},identification_number = #{identificationNumber},phone = #{phone}, permission_status = #{permissionStatus},position = #{position}, password = #{password},campus_id = #{campusId},updated_at = #{updatedAt} where teacher_id = #{teacherId}")
    int update(Teacher teacher);

    @Select("select * from userManagement.teacher where teacher_id = #{teacherId}")
    Teacher selectTeacherById(@Param("teacherId") Integer teacherId);
}

