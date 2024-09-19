package com.chuanglian.mingpin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.chuanglian.mingpin.entity.point.Point;
import com.chuanglian.mingpin.pojo.PointRecordsVo;

import java.util.List;

public interface PointService  {

    Point selectPoint(Integer studentId);

    List<Point> selectPointByClass(Integer classId);

    String clearPointByClassId(Integer classId);

    String adjustPoints(Integer studentId, Integer change,String type);

//    PointRecordsVo selectPointRecords(Integer studentId);
}
