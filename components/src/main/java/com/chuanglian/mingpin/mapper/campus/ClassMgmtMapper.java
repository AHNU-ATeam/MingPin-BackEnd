package com.chuanglian.mingpin.mapper.campus;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chuanglian.mingpin.entity.campus.Class;
import com.chuanglian.mingpin.entity.user.Student;
import com.chuanglian.mingpin.entity.user.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
@TableName("[campusManagement].[class]")
public interface ClassMgmtMapper extends BaseMapper<Class> {
    @Select("select student_id from campusManagement.class_student where class_id = #{id}")
    List<Integer> getStudentsId(Integer id);

//    @Select("select * from userManagement.student where student_id in #{stuId}")
    List<Student> getStudents(List<Integer> stuIds);

    // 给 num 字段加1
    @Update("UPDATE [campusManagement].[class] SET num = num + 1 WHERE class_id = #{classId}")
    void incrementClassNumById(int classId);

    // 给 num 字段减1
    @Update("UPDATE [campusManagement].[class] SET num = num - 1 WHERE class_id = #{classId}")
    void decrementClassNumById(int classId);
    List<Teacher> getAssistants(List<Integer> assistantIds);

    // 自定义SQL查询，根据userId查询classId
    @Select("SELECT class_id FROM [campusManagement].[class] WHERE user_id = #{userId}")
    Integer findClassIdsByUserId(Integer userId);
}
