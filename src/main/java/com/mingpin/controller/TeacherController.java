package com.mingpin.controller;

import com.mingpin.pojo.Result;
import com.mingpin.pojo.Teacher;
import com.mingpin.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    /**
     * 展示所有教师信息
     * @return
     */
    @GetMapping("/showTeacherMessage")
    public Result show(){
        log.info("展示所有教师信息");
        List<Teacher> list = teacherService.list();
        return Result.success(list);
    }

    /**
     * 新增教师
     * @param teacher
     * @return
     */
    @PostMapping("/addTeacher")
    public Result add(@RequestBody Teacher teacher){
        log.info("新增教师: {}" , teacher);
        teacherService.add(teacher);
        return Result.success();
    }

    /**
     * 根据id删除教师
     * @param id
     * @return
     */
    @DeleteMapping("deleteById/{id}")
    public Result deleteById(@PathVariable Integer id){
        log.info("删除教师");
        teacherService.delete(id);
        return Result.success();
    }

    /**
     * 更新信息
     * @param teacher
     * @return
     */
    @PutMapping("/update")
    public Result update(@RequestBody Teacher teacher){
        log.info("更新教师信息");
        teacherService.update(teacher);
        return Result.success();
    }

    /**
     * 根据id查询单个教师信息
     * @param teacherId
     * @return
     */
    @GetMapping("/searchById/{teacherId}")
    public Result getTeacherById(@PathVariable Long teacherId) {
        Teacher teacher = teacherService.getTeacherById(teacherId);

            return Result.success(teacher);

    }
}
