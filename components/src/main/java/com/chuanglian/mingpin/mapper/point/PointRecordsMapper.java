package com.chuanglian.mingpin.mapper.point;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chuanglian.mingpin.entity.point.PointRecords;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PointRecordsMapper extends BaseMapper<PointRecords> {

    Integer insertRecords(List<PointRecords> records);
}
