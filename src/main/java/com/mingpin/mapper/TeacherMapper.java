package com.mingpin.mapper;

import com.mingpin.pojo.Teacher;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TeacherMapper {
    @Select("select * from mingpin.userManagement.teacher")
     List<Teacher> list();


    @Insert("insert into userManagement.teacher(name, phone, permission_status, position, campus_id) values (#{name},#{phone},#{permissionStatus},#{position},#{campusId})")
    void add(Teacher teacher);

    @Delete("delete from userManagement.teacher where teacher_id = #{id}")
    void delete(Integer id);

    void update(Teacher teacher);
}
