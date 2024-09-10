package com.chuanglian.mingpin.service;

import com.chuanglian.mingpin.entity.point.PointRecords;
import com.chuanglian.mingpin.entity.point.vo.PointRecordsVo;

import java.util.List;


public interface PointRecordsService {
//    int addPointRecords(PointRecords pointRecords);

    List<PointRecordsVo> selectPointRecordsByClass(Integer classId);

    List<PointRecordsVo> selectPointRecords(Integer studentId);
}
