package com.chuanglian.mingpin.controller;

import com.chuanglian.mingpin.entity.homework.HomeworkSubmission;
import com.chuanglian.mingpin.entity.homework.vo.CorrectSubmissionVo;
import com.chuanglian.mingpin.pojo.Result;
import com.chuanglian.mingpin.service.HomeworkSubmissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/homeworkSubmission")
@Api(tags = "作业提交管理")
public class HomeworkSubmissionController {

    @Autowired
    private HomeworkSubmissionService homeworkSubmissionService;

    @PostMapping("/submit")
    @PreAuthorize("hasAuthority('sys:homeworkSubmission:submit')")
    @ApiOperation(value = "提交作业", notes = "该接口用于学生提交作业")
    public Result submit(@RequestBody @Validated HomeworkSubmission homeworkSubmission) {
        homeworkSubmissionService.submit(homeworkSubmission);
        return Result.success();
    }

    @PostMapping("/delete/{submissionId}")
    @PreAuthorize("hasAuthority('sys:homeworkAssignment:delete')")
    @ApiOperation(value = "删除作业", notes = "该接口用于学生或老师删除作业")
    public Result delete(@PathVariable Integer submissionId) {
        homeworkSubmissionService.delete(submissionId);
        return Result.success();
    }

    @PostMapping("/update/{submissionId}")
    @PreAuthorize("hasAuthority('sys:homeworkAssignment:update')")
    @ApiOperation(value = "修改作业", notes = "该接口用于老师或学生修改作业")
    public Result update(@RequestBody @Validated HomeworkSubmission homeworkSubmission) {
        homeworkSubmissionService.update(homeworkSubmission);
        return Result.success();
    }

    @GetMapping("/select/{submissionId}")
    @PreAuthorize("hasAuthority('sys:homeworkAssignment:select')")
    @ApiOperation(value = "通过id查询作业", notes = "该接口用于查询单个作业")
    public Result<HomeworkSubmission> selectById(@PathVariable Integer submissionId) {
        return Result.success(homeworkSubmissionService.selectById(submissionId));
    }

    @GetMapping("/select/student/{studentId}")
    @PreAuthorize("hasAuthority('sys:homeworkAssignment:selectByStudent')")
    @ApiOperation(value = "查询学生所有作业", notes = "该接口用于老师或学生根据学生id查询作业")
    public Result<List<HomeworkSubmission>> selectByStudent(@PathVariable Integer studentId) {
        return Result.success(homeworkSubmissionService.selectByStudent(studentId));
    }

    @GetMapping("/select/assignment/{assignmentId}")
    @PreAuthorize("hasAuthority('sys:homeworkAssignment:selectByAssignment')")
    @ApiOperation(value = "查询所有提交的作业", notes = "该接口用于老师查询自己发布的作业下的所有提交")
    public Result<List<HomeworkSubmission>> selectBySubmission(@PathVariable Integer assignmentId) {
        return Result.success(homeworkSubmissionService.selectBySubmission(assignmentId));
    }

    @PostMapping("/correct")
    @PreAuthorize("hasAuthority('sys:homeworkAssignment:correct')")
    @ApiOperation(value = "批改作业", notes = "该接口用于老师批改自己发布的作业或更正自己批改的作业")
    public Result correct(@RequestBody CorrectSubmissionVo correctSubmissionVo) {
        homeworkSubmissionService.correct(correctSubmissionVo);
        return Result.success();
    }

    @GetMapping("/select/submitStatus/{assignmentId}/{submitStatus}")
    @PreAuthorize("hasAuthority('sys:homeworkAssignment:selectByStatus')")
    @ApiOperation(value = "通过提交状态查询所有的作业", notes = "该接口用于老师通过提交状态查看自己发布的作业的提交情况")
    public Result<List<HomeworkSubmission>> selectBySubmitStatus(@PathVariable Integer assignmentId, @PathVariable Integer submitStatus) {
        return Result.success(homeworkSubmissionService.selectBySubmitStatus(assignmentId, submitStatus));
    }

}
