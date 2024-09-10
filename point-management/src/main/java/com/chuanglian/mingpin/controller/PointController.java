package com.chuanglian.mingpin.controller;

import com.chuanglian.mingpin.entity.point.Point;
import com.chuanglian.mingpin.entity.point.PointRecords;
import com.chuanglian.mingpin.entity.point.vo.PointRecordsVo;
import com.chuanglian.mingpin.service.PointService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.chuanglian.mingpin.pojo.Result;

import java.util.List;

@RestController
@RequestMapping("point")
public class PointController {
    @Autowired
    private PointService pointService;

    /**
     * 根据学生id，查询积分信息
     * @param studentId
     * @return
     */
    @GetMapping("student/{student_id}")
    public Result selectPoint(@PathVariable("student_id") Integer studentId){

        return Result.success(pointService.selectPoint(studentId));
    }

    /**
     * 根据班级信息查询所有学生的积分信息
     * @param classId
     * @return
     */
    @GetMapping("class/{class_id}")
    public Result selectPointByClass(@PathVariable("class_id")Integer classId){
        return Result.success(pointService.selectPointByClass(classId));
    }

    /**
     * 根据教室id，清零学生的积分
     * @param classId
     * @return
     */
    @GetMapping("clear/{class_id}")
    public Result clearPointByClassId(@PathVariable("class_id")Integer classId){
        return Result.success(pointService.clearPointByClassId(classId));
    }

    /**
     * 变化学生的信息
     * @param studentId
     * @param change
     * @param type
     * @return
     */
    @PostMapping("change")
    public Result adjustPoints(@RequestParam("student_id")Integer studentId,
                               @RequestParam("change")Integer change,
                               @RequestParam("type")String type){
        return Result.success(pointService.adjustPoints(studentId,change,type));
    }

//    /**
//     * 查找学生的积分变化
//     * @param studentId
//     * @return
//     */
//    @GetMapping("records/{student_id}")
//    public Result selectPointRecords(@PathVariable("student_id")Integer studentId){
//        PointRecordsVo pointRecords = pointService.selectPointRecords(studentId);
//        return Result.success(pointRecords);
//    }
}
