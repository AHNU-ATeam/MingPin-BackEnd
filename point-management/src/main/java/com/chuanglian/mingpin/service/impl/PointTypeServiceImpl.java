package com.chuanglian.mingpin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chuanglian.mingpin.entity.point.PointType;
import com.chuanglian.mingpin.mapper.point.PointTypeMapper;
import com.chuanglian.mingpin.service.PointTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PointTypeServiceImpl extends ServiceImpl<PointTypeMapper,PointType> implements PointTypeService{

    @Autowired
    private PointTypeMapper pointTypeMapper;
    @Override
    public List<PointType> selectPointType(Integer campusId) {
        LambdaQueryWrapper<PointType> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PointType::getCampusId,campusId)
                .ne(PointType::getType,"签到");
        List<PointType> pointTypes = pointTypeMapper.selectList(wrapper);

        return pointTypes;
    }

    @Override
    public boolean addPointTypes(PointType pointType) {
        boolean save = save(pointType);
        if(!save) throw new RuntimeException("添加失败");
        return save;
    }

    @Override
    public boolean deletePointType(Integer id) {
        boolean b = removeById(id);
        if(!b) throw new RuntimeException("删除失败");
        return b;
    }

    @Override
    public boolean updatePointType(PointType pointType) {
        boolean b = updateById(pointType);
        if(!b) throw new RuntimeException("修改失败");
        return b;
    }

    @Override
    public PointType getRegister(Integer id) {
        PointType pointType = pointTypeMapper.selectOne(new LambdaQueryWrapper<PointType>().eq(PointType::getCampusId, id).eq(PointType::getType, "签到"));
        return pointType;
    }


}
