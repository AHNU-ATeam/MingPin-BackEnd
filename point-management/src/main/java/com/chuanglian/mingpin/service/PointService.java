package com.chuanglian.mingpin.service;


import com.chuanglian.mingpin.pojo.PointDto;

import java.util.List;

public interface PointService  {

    PointDto selectPoint(Integer studentId);

    List<PointDto> selectPointByClass(Integer classId);

    String clearPointByClassId(Integer classId);

    String adjustPoints(Integer studentId, Integer change,String type);

//    PointRecordsVo selectPointRecords(Integer studentId);
}
