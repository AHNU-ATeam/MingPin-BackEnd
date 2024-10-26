package com.chuanglian.mingpin.controller;

import com.chuanglian.mingpin.entity.point.PointType;
import com.chuanglian.mingpin.pojo.Result;
import com.chuanglian.mingpin.service.PointTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public Result addPointType(@RequestBody PointType pointType){
        return Result.success(pointTypeService.addPointTypes(pointType));
    }

    @GetMapping("/delect/{id}")
    public Result deletePointType(@PathVariable("id") Integer id){
        return Result.success(pointTypeService.deletePointType(id));
    }

    @PostMapping("/update")
    public Result updatePointType(@RequestBody PointType pointType){
        return Result.success(pointTypeService.updatePointType(pointType));
    }
    
}
