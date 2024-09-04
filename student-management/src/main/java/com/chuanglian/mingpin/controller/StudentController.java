package com.chuanglian.mingpin.controller;

import com.chuanglian.mingpin.entity.user.Student;
import com.chuanglian.mingpin.pojo.Result;
import com.chuanglian.mingpin.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentService studentService;

    //根据校区查询所有学生信息
    @GetMapping("/campusList")
    public Result<List<Student>> campusList(@PathVariable Integer campusId) {
        List<Student> Ls = studentService.campusList(campusId);
        return Result.success(Ls);
    }

    //根据班级查询所有学生信息
    @GetMapping("/classList")
    public Result<List<Student>> classList(@PathVariable Integer classId) {
        List<Student> Ls = studentService.classList(classId);
        return Result.success(Ls);
    }

    //查询单个学生信息
    @GetMapping
    public Result<Student> detail(@PathVariable Integer studentId) {
        Student student = studentService.findById(studentId);
        return Result.success(student);
    }

    //新增学生个人信息
    @PostMapping
    public Result add(@RequestBody Student student) {
        studentService.add(student);
        return Result.success();
    }

    //修改学生个人信息
    @PutMapping
    public Result update(@RequestBody @Validated Student student) {
        studentService.update(student);
        return Result.success();
    }

    //删除学生个人信息
    @DeleteMapping
    public Result delete(Integer studentId) {
        studentService.deleteById(studentId);
        return Result.success();
    }
}

