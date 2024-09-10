package com.chuanglian.mingpin.service;

import com.chuanglian.mingpin.entity.point.PointType;

import java.util.List;

public interface PointTypeService {
    List<PointType> selectPointType(Integer campusId);
}
