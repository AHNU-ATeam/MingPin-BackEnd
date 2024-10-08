package com.chuanglian.mingpin.controller;

import com.chuanglian.mingpin.entity.homework.DTO.AssignmentDTO;
import com.chuanglian.mingpin.entity.homework.DTO.UpdateAssignmentDTO;
import com.chuanglian.mingpin.entity.homework.VO.AssignmentVO;
import com.chuanglian.mingpin.service.HomeworkAssignmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.chuanglian.mingpin.pojo.Result;

import java.util.List;

@RestController
@RequestMapping("/homeworkAssignment")
@Api(tags = "作业发布管理")
public class HomeworkAssignmentController {

    @Autowired
    private HomeworkAssignmentService homeworkAssignmentService;

    @PostMapping("/publish")
    @PreAuthorize("hasAuthority('sys:homeworkAssignment:publish')")
    @ApiOperation(value = "发布作业", notes = "该接口用于学生发布作业")
    public Result publish(@RequestBody @Validated AssignmentDTO AssignmentDTO){
        homeworkAssignmentService.publish(AssignmentDTO);
        return Result.success();
    }

    @GetMapping("/select/{assignmentId}")
    @PreAuthorize("hasAuthority('sys:homeworkAssignment:select')")
    @ApiOperation(value = "获取作业信息", notes = "该接口用于获取发布的作业详情")
    public Result<AssignmentVO> detail(@PathVariable Integer assignmentId) {
        return Result.success(homeworkAssignmentService.findById(assignmentId));
    }

    @GetMapping("/select/student/{studentUserId}")
    @PreAuthorize("hasAuthority('sys:homeworkAssignment:select')")
    @ApiOperation(value = "通过学生id获取作业列表", notes = "该接口用于通过学生id获取所有的作业")
    public Result<List<AssignmentVO>> findByStudent(@PathVariable Integer studentUserId) {
        return Result.success(homeworkAssignmentService.findByStudent(studentUserId));
    }

    @PostMapping("/delete/{assignmentId}")
    @PreAuthorize("hasAuthority('sys:homeworkAssignment:delete')")
    @ApiOperation(value = "删除作业", notes = "该接口用于通过id删除作业信息")
    public Result delete(@PathVariable Integer assignmentId) {
        homeworkAssignmentService.delete(assignmentId);
        return Result.success();
    }

    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sys:homeworkAssignment:update')")
    @ApiOperation(value = "修改作业", notes = "该接口用于通过id修改作业信息")
    public Result update(@RequestBody @Validated UpdateAssignmentDTO updateAssignmentDTO) {
        homeworkAssignmentService.update(updateAssignmentDTO);
        return Result.success();
    }

}
