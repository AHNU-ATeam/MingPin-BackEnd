package com.chuanglian.mingpin.controller;

import com.chuanglian.mingpin.entity.point.PointType;
import com.chuanglian.mingpin.pojo.Result;
import com.chuanglian.mingpin.service.PointTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("PointType")
public class PointTypeController {
    @Autowired
    private PointTypeService pointTypeService;
    @GetMapping("{campus_id}")
    public Result selectPointType(@PathVariable("campus_id") Integer campusId){
        List<PointType> pointTypes = pointTypeService.selectPointType(campusId);
        return Result.success(pointTypes);
    }
}
