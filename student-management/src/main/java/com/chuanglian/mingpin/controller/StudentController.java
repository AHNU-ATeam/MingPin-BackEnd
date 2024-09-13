package com.chuanglian.mingpin.controller;

import com.chuanglian.mingpin.entity.user.Student;
import com.chuanglian.mingpin.pojo.Result;
import com.chuanglian.mingpin.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
@Api(tags = "学生管理")
public class StudentController {
    @Autowired
    StudentService studentService;

    @GetMapping("/campusList/{campusId}")
    @PreAuthorize("hasAuthority('sys:student:select')")
    @ApiOperation(value = "通过校区id查询学生", notes = "该接口主要用于通过校区id查询所有学生")
    public Result<List<Student>> campusList(@PathVariable Integer campusId) {
        List<Student> students = studentService.campusList(campusId);
        return Result.success(students);
    }

    @GetMapping("/classList/{classId}")
    @PreAuthorize("hasAuthority('sys:student:select')")
    @ApiOperation(value = "通过班级id查询学生", notes = "该接口主要用于通过班级id查询所有学生")
    public Result<List<Student>> classList(@PathVariable Integer classId) {
        List<Student> students = studentService.classList(classId);
        return Result.success(students);
    }

    @GetMapping("/{studentId}")
    @PreAuthorize("hasAuthority('sys:student:select')")
    @ApiOperation(value = "通过id查询学生", notes = "该接口主要用于通过学生id查询该学生")
    public Result<Student> detail(@PathVariable Integer studentId) {
        Student student = studentService.findById(studentId);
        return Result.success(student);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('sys:student:add')")
    @ApiOperation(value = "通过id添加学生", notes = "该接口主要用于添加学生信息")
    public Result add(@RequestBody @Validated Student student) {
        studentService.add(student);
        return Result.success();
    }

    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sys:student:update')")
    @ApiOperation(value = "通过id修改学生", notes = "该接口主要用于更新学生信息")
    public Result update(@RequestBody @Validated Student student) {
        studentService.update(student);
        return Result.success();
    }

    @PostMapping("/delete/{studentId}")
    @PreAuthorize("hasAuthority('sys:student:delete')")
    @ApiOperation(value = "通过id删除学生", notes = "该接口主要用于删除学生信息")
    public Result delete(@PathVariable Integer studentId) {
        studentService.deleteById(studentId);
        return Result.success();
    }
}