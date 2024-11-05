package com.chuanglian.mingpin.controller;

import com.chuanglian.mingpin.entity.user.dto.StudentDTO;
import com.chuanglian.mingpin.entity.user.dto.UpdateStudentDTO;
import com.chuanglian.mingpin.entity.user.vo.StudentInfoVO;
import com.chuanglian.mingpin.entity.user.vo.StudentVO;
import com.chuanglian.mingpin.pojo.Result;
import com.chuanglian.mingpin.security.Log;
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
    public Result<List<StudentVO>> campusList(@PathVariable Integer campusId) {
        return Result.success(studentService.campusList(campusId));
    }

    @Log("获取班级里学生信息")
    @GetMapping("/classList/{classId}")
    @PreAuthorize("hasAuthority('sys:student:select')")
    @ApiOperation(value = "通过班级id查询学生", notes = "该接口主要用于通过班级id查询所有学生")
    public Result<List<StudentVO>> classList(@PathVariable Integer classId) {
        return Result.success(studentService.classList(classId));
    }

    @GetMapping("/{studentId}")
    @PreAuthorize("hasAuthority('sys:student:select')")
    @ApiOperation(value = "通过id查询学生", notes = "该接口主要用于通过学生id查询该学生")
    public Result<StudentInfoVO> detail(@PathVariable Integer studentId) {
        return Result.success(studentService.findById(studentId));
    }

    @GetMapping("/keyWordList")
    @PreAuthorize("hasAuthority('sys:student:select')")
    @ApiOperation(value = "关键字查询学生", notes = "该接口主要用于通过关键字模糊查询学生")
    public Result<List<StudentVO>> keyWordList(@RequestParam String keyWord) {
        return Result.success(studentService.keyWordList(keyWord));
    }

    @GetMapping("/teacherList/{teacherId}")
    @PreAuthorize("hasAuthority('sys:student:select')")
    @ApiOperation(value = "通过教师id查询学生", notes = "该接口主要用于通过教师id查询学生")
    public Result<List<StudentVO>> teacherList(@PathVariable Integer teacherId) {
        return Result.success(studentService.teacherList(teacherId));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('sys:student:add')")
    @ApiOperation(value = "添加学生", notes = "该接口主要用于添加学生信息")
    public Result add(@RequestBody StudentDTO studentDTO) {
        studentService.add(studentDTO);
        return Result.success();
    }

    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sys:student:update')")
    @ApiOperation(value = "通过id修改学生", notes = "该接口主要用于更新学生信息")
    public Result update(@RequestBody @Validated UpdateStudentDTO updateStudentDTO) {
        studentService.update(updateStudentDTO);
        return Result.success();
    }

    @PostMapping("/delete/{studentId}")
    @PreAuthorize("hasAuthority('sys:student:delete')")
    @ApiOperation(value = "通过id删除学生", notes = "该接口主要用于删除学生信息")
    public Result delete(@PathVariable Integer studentId) {
        studentService.deleteById(studentId);
        return Result.success();
    }

    @GetMapping("/campus/keyWord")
    @PreAuthorize("hasAuthority('sys:student:select')")
    @ApiOperation(value = "通过校区id和关键字查询学生", notes = "该接口主要用于通过校区id和关键字查询学生")
    public Result<List<StudentVO>> campusKeyWordList(@RequestParam Integer campusId, @RequestParam String keyWord) {
        return Result.success(studentService.campusKeyWordList(campusId, keyWord));
    }

    @GetMapping("/class/keyWord")
    @PreAuthorize("hasAuthority('sys:student:select')")
    @ApiOperation(value = "通过班级id和关键字查询学生", notes = "该接口主要用于通过班级id和关键字查询学生")
    public Result<List<StudentVO>> classKeyWordList(@RequestParam Integer campusId, @RequestParam Integer classId, @RequestParam String keyWord) {
        return Result.success(studentService.classKeyWordList(campusId, classId, keyWord));
    }
}