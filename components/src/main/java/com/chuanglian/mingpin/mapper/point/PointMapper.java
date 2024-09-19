package com.chuanglian.mingpin.mapper.point;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chuanglian.mingpin.entity.point.Point;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface PointMapper extends BaseMapper<Point> {

    Integer resetPointsByStudentIds(List<Integer> studentIds);
}
