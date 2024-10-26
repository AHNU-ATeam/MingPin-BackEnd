package com.chuanglian.mingpin.controller;

import com.chuanglian.mingpin.entity.leave.dto.StudentLeaveDTO;
import com.chuanglian.mingpin.entity.leave.dto.TeacherLeaveDTO;
import com.chuanglian.mingpin.entity.leave.vo.LeaveVO;
import com.chuanglian.mingpin.pojo.Result;
import com.chuanglian.mingpin.service.LeaveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leave")
@Api(tags = "请假管理")
public class LeaveController {
    private final LeaveService leaveService;

    public LeaveController(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

    @PostMapping("/apply")
    //@PreAuthorize("hasAuthority('sys:leave:apply')")
    @ApiOperation(value = "申请请假", notes = "该接口用于用户申请请假")
    public Result apply(@RequestBody @Validated StudentLeaveDTO leaveDTO) {
        leaveService.apply(leaveDTO);
        return Result.success();
    }

    @GetMapping("/detail/{leaveId}")
    //@PreAuthorize("hasAuthority('sys:leave:detail')")
    @ApiOperation(value = "获取请假详情", notes = "该接口用于获取具体的请假信息")
    public Result<LeaveVO> detail(@PathVariable Integer leaveId) {
        return Result.success(leaveService.findById(leaveId));
    }

    @GetMapping("/listByUser/{userId}")
    //@PreAuthorize("hasAuthority('sys:leave:list')")
    @ApiOperation(value = "通过用户id获取请假列表", notes = "该接口用于通过用户id获取所有的请假记录")
    public Result<List<LeaveVO>> listByUser(@PathVariable Integer userId) {
        return Result.success(leaveService.findByUser(userId));
    }

    @PostMapping("/delete/{leaveId}")
    //@PreAuthorize("hasAuthority('sys:leave:delete')")
    @ApiOperation(value = "删除请假记录", notes = "该接口用于通过id删除请假记录")
    public Result delete(@PathVariable Integer leaveId) {
        leaveService.delete(leaveId);
        return Result.success();
    }

    @PostMapping("/update")
    //@PreAuthorize("hasAuthority('sys:leave:update')")
    @ApiOperation(value = "修改请假记录", notes = "该接口用于通过id修改请假记录")
    public Result update(@RequestBody @Validated StudentLeaveDTO leaveDTO) {
        leaveService.update(leaveDTO);
        return Result.success();
    }

    @GetMapping("/listByTeacher/{teacherId}")
    //@PreAuthorize("hasAuthority('sys:leave:list')")
    @ApiOperation(value = "通过老师id获取请假列表", notes = "该接口用于通过老师id获取所有的请假记录")
    public Result<List<LeaveVO>> listByTeacher(@PathVariable Integer teacherId) {
        return Result.success(leaveService.findByTeacher(teacherId));
    }

    @GetMapping("/listByClass/{classId}")
    //@PreAuthorize("hasAuthority('sys:leave:list')")
    @ApiOperation(value = "通过班级id获取请假列表", notes = "该接口用于通过班级id获取所有的请假记录")
    public Result<List<LeaveVO>> listByClass(@PathVariable Integer classId) {
        return Result.success(leaveService.findByClass(classId));
    }

    @PostMapping("/updateByTeacher")
    //@PreAuthorize("hasAuthority('sys:leave:update')")
    @ApiOperation(value = "修改请假记录", notes = "该接口用于通过id修改请假记录，仅限教师操作")
    public Result updateByTeacher(@RequestBody @Validated TeacherLeaveDTO leaveDTO) {
        leaveService.updateByTeacher(leaveDTO);
        return Result.success();
    }

}
