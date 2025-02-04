package com.chuanglian.mingpin.controller;

import com.chuanglian.mingpin.pojo.CampusTeacherVO;
import com.chuanglian.mingpin.pojo.Result;
import com.chuanglian.mingpin.pojo.TeacherVoForUpdate;
import com.chuanglian.mingpin.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    /**
     * 展示所有教师信息
     * @return
     */
    @RequestMapping(value = "/showTeacherMessage/{campusId}", method = RequestMethod.POST)
    public Result show(@PathVariable Integer campusId){
        log.info("展示所有教师信息");
        return Result.success(teacherService.getAllTeacherUsers(campusId));
    }

    /**
     * 新增教师
     * @param campusTeacherVO
     * @return
     * @throws IOException
     */
    @PostMapping("/addTeacher")
    public Result add( @ModelAttribute CampusTeacherVO campusTeacherVO) throws IOException {
        log.info("新增教师: {}" , campusTeacherVO);
        return teacherService.add(campusTeacherVO);
    }

    /**
     *软删除：将教师id为teacherId的is_deleted设置为1
     * @param teacherId
     * @return
     */
    //@DeleteMapping("/deleteById/{teacherId}")
    @RequestMapping(value = "/deleteById/{teacherId}", method = RequestMethod.POST)
    public Result deleteById(@PathVariable Integer teacherId){
        log.info("删除教师");
        return teacherService.delete(teacherId);
    }

    /**
     * 更新教师信息
     * @param teacherVoForUpdate
     * @return
     * @throws IOException
     */
    @PostMapping(value = "/update")
    public Result update(@ModelAttribute TeacherVoForUpdate teacherVoForUpdate) throws IOException {
        log.info("更新教师信息");
        //将userId先转换为teacherId，统一id
        Integer teacherId = teacherService.getTeacherIdByUserId(teacherVoForUpdate.getTeacherId());
        teacherVoForUpdate.setTeacherId(teacherId);
        return teacherService.update(teacherVoForUpdate);
    }

    /**
     * 根据id查询单个教师信息
     * @param teacherId
     * @return
     */
    @GetMapping("/searchById/{teacherId}")
    public Result getTeacherById(@PathVariable Integer teacherId) {
        log.info("查询教师");

        //将userId先转换为teacherId，统一id
        teacherId = teacherService.getTeacherIdByUserId(teacherId);

        return teacherService.getTeacherById(teacherId);
    }

    /**
     * 根据userId查询教师信息,   尽量别用
     * @param userId
     * @return
     */
    @GetMapping("/searchByUserId/{userId}")
    public Result getTeacherByUserId(@PathVariable Integer userId) {
        log.info("查询教师");
        return teacherService.getTeacherByUserId(userId);
    }
}
