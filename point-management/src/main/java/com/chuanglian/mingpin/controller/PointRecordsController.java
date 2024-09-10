package com.chuanglian.mingpin.controller;

import com.chuanglian.mingpin.entity.point.PointRecords;
import com.chuanglian.mingpin.pojo.Result;
import com.chuanglian.mingpin.service.PointRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("PointRecords")
public class PointRecordsController {
    @Autowired
    private PointRecordsService pointRecordsService;

//    @PostMapping
//    public Result addPointRecords(@RequestBody PointRecords pointRecords){
//        return Result.success(pointRecordsService.addPointRecords(pointRecords));
//
//    }

    @GetMapping("class/{class_id}")
    public Result selectPointRecordsByClass(@PathVariable("class_id")Integer classId){
        return Result.success(pointRecordsService.selectPointRecordsByClass(classId));
    }

    @GetMapping("student/{student_id}")
    public Result selectPointRecords(@PathVariable("student_id")Integer studentId){
        return Result.success(pointRecordsService.selectPointRecords(studentId));
    }
}
