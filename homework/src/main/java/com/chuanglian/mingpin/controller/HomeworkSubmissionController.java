package com.chuanglian.mingpin.controller;

import com.chuanglian.mingpin.entity.homework.HomeworkSubmission;
import com.chuanglian.mingpin.pojo.Result;
import com.chuanglian.mingpin.service.HomeworkSubmissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/homeworkSubmission")
@Api(tags = "作业提交管理")
public class HomeworkSubmissionController {

    @Autowired
    private HomeworkSubmissionService homeworkSubmissionService;

    @PostMapping("/submit")
    @PreAuthorize("hasAuthority('sys:homeworkSubmission:submit')")
    @ApiOperation(value = "提交作业", notes = "该接口用于老师提交作业")
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

    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sys:homeworkAssignment:update')")
    @ApiOperation(value = "修改作业", notes = "该接口用于老师修改作业")
    public Result update(@RequestBody @Validated HomeworkSubmission homeworkSubmission) {
        homeworkSubmissionService.update(homeworkSubmission);
        return Result.success();
    }

    @GetMapping("/select/{assignmentId}")
    @PreAuthorize("hasAuthority('sys:homeworkAssignment:select')")
    @ApiOperation(value = "通过id查询作业", notes = "该接口用于查询单个作业")
    public Result<HomeworkSubmission> selectById(@PathVariable Integer assignmentId) {
        return Result.success(homeworkSubmissionService.selectById(assignmentId));
    }

}
