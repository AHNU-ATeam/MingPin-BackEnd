package com.chuanglian.mingpin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chuanglian.mingpin.entity.point.PointType;

import java.util.List;

public interface PointTypeService extends IService<PointType> {
    List<PointType> selectPointType(Integer campusId);

    boolean addPointTypes(PointType pointType);

    boolean deletePointType(Integer id);

    boolean updatePointType(PointType pointType);
}
