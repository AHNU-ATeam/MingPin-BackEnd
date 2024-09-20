package com.chuanglian.mingpin.service;

import com.chuanglian.mingpin.pojo.PointRecordsVo;

import java.util.List;


public interface PointRecordsService {
//    int addPointRecords(PointRecords pointRecords);

    List<PointRecordsVo> selectPointRecordsByClass(Integer classId);

    List<PointRecordsVo> selectPointRecords(Integer studentId);
}
