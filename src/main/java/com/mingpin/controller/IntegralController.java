package com.mingpin.controller;

import com.mingpin.pojo.Result;
import com.mingpin.pojo.StudentRankingDTO;
import com.mingpin.service.IntegralService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
public class IntegralController {


    @Autowired
    private IntegralService integralService;




    /**
     * 输入积分数，为学生增加id 前端发送：学生id，增加积分数
     * @param points
     * @return
     */
    @RequestMapping("/addPointsById")
    public Result addPoints(@RequestParam Long studentId, @RequestParam Integer points){
        log.info("为学生增加积分");
        integralService.addPoints(studentId,points);

        return Result.success();

    }

    //班级排行榜

    /**
     * 前端传入该班级名称
     * 获取班级内部积分排行榜
     * @param className
     * @return
     */
    @GetMapping("/classRanking")
    public Result inClassRank(@PathVariable String className){
        List<StudentRankingDTO> rankings = integralService.getStudentRankingInClass(className);
        return Result.success(rankings);
    }



    /**
     * 获取年级排行榜
     * @param gradeName
     * @return
     */
    @GetMapping("/gradeRanking")
    public Result inGradeRank(@PathVariable String gradeName){
        List<StudentRankingDTO> rankings = integralService.getStudentRankingInGrade(gradeName);
        return Result.success(rankings);
    }


    /**
     * 每周积分数
     * @param studentId
     * @return
     */
    @GetMapping("/weeklyPoints/{studentId}")
    public Result getWeeklyPoints(@PathVariable Long studentId) {
        int points = integralService.getWeeklyPoints(studentId);
        return Result.success(points);
    }

    /**
     * 每月积分数
     * @param studentId
     * @return
     */
    @GetMapping("/monthlyPoints/{studentId}")
    public Result getMonthlyPoints(@PathVariable Long studentId) {
        int points = integralService.getMonthlyPoints(studentId);
        return Result.success(points);
    }

    /**
     * 每年积分数
     * @param studentId
     * @param year
     * @return
     */

    @GetMapping("/annualPoints/{studentId}/{year}")
    public Result getAnnualPoints(@PathVariable Long studentId, @PathVariable int year) {
        int points = integralService.getAnnualPoints(studentId, year);
        return Result.success(points);
    }
}
