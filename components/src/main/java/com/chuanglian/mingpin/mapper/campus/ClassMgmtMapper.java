package com.chuanglian.mingpin.mapper.campus;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chuanglian.mingpin.entity.campus.Class;
import com.chuanglian.mingpin.entity.user.Student;
import com.chuanglian.mingpin.entity.user.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
@TableName("[campusManagement].[class]")
public interface ClassMgmtMapper extends BaseMapper<Class> {
    @Select("select student_id from campusManagement.class_student where class_id = #{id}")
    List<Integer> getStudentsId(Integer id);

//    @Select("select * from userManagement.student where student_id in #{stuId}")
    List<Student> getStudents(List<Integer> stuIds);


    List<Teacher> getAssistants(List<Integer> assistantIds);
}
