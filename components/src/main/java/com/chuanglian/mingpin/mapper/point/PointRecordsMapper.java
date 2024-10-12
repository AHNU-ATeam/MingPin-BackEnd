package com.chuanglian.mingpin.mapper.point;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chuanglian.mingpin.entity.point.PointRecords;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PointRecordsMapper extends BaseMapper<PointRecords> {

    Integer insertRecords(List<PointRecords> records);

    // 删除学生积分记录
    @Delete("DELETE FROM pointManagement.point_records WHERE student_id = #{studentId}")
    int deleteStudentPointRecords(@Param("studentId") int studentId);
}
