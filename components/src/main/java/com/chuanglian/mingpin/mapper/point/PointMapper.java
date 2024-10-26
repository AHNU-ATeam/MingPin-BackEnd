package com.chuanglian.mingpin.mapper.point;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chuanglian.mingpin.entity.point.Point;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface PointMapper extends BaseMapper<Point> {

    Integer resetPointsByStudentIds(List<Integer> studentIds);
    // 创建学生积分
    @Insert("INSERT INTO pointManagement.point (student_id, point) VALUES (#{studentId}, #{point})")
    int createStudentPoint(@Param("studentId") int studentId, @Param("point") int point);

    // 删除学生积分
    @Delete("DELETE FROM pointManagement.point WHERE student_id = #{studentId}")
    int deleteStudentPoint(@Param("studentId") int studentId);
}
