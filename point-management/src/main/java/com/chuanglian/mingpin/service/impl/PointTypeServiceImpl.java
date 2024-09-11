package com.chuanglian.mingpin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chuanglian.mingpin.entity.point.PointType;
import com.chuanglian.mingpin.mapper.point.PointTypeMapper;
import com.chuanglian.mingpin.service.PointTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PointTypeServiceImpl implements PointTypeService {

    @Autowired
    private PointTypeMapper pointTypeMapper;
    @Override
    public List<PointType> selectPointType(Integer campusId) {
        LambdaQueryWrapper<PointType> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PointType::getCampusId,campusId);
        List<PointType> pointTypes = pointTypeMapper.selectList(wrapper);

        return pointTypes;
    }
}
