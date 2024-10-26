package com.chuanglian.mingpin.controller;

import com.chuanglian.mingpin.entity.attendance.EmployeeAttendance;
import com.chuanglian.mingpin.entity.attendance.EmpAttendDownload;
import com.chuanglian.mingpin.entity.attendance.StuAttendDownload;
import com.chuanglian.mingpin.pojo.Result;
import com.chuanglian.mingpin.service.EmpAttendService;
import com.chuanglian.mingpin.service.StuAttendService;

import com.chuanglian.mingpin.entity.attendance.utils.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/Attendance")
public class AttendanceController {
    @Autowired
    StuAttendService stuAttendService;
    @Autowired
    EmpAttendService empAttendService;

    /**
     * 学生打卡
     * @param id
     * @param photo
     * @return
     */
//    @PostMapping("/stu")
//    public Result stuAttendance(@RequestParam("id") Integer id,
//                                @RequestParam("photo")String photo){
//        return Result.success(stuAttendService.stuAttendance(id,photo));
//    }
    @PostMapping("/stu")
    public Result stuAttendance(@RequestBody Map<String, Object> requestBody) {
        Integer id = (Integer) requestBody.get("id");
        String photo = (String) requestBody.get("photo");
        return Result.success(stuAttendService.stuAttendance(id, photo));
    }


    /**
     * 学生签退
     * @param id
     * @return
     */
    @PostMapping("/stu/out")
    public Result stuCheckOut(@RequestParam("id") Integer id){
        return Result.success(stuAttendService.stuCheckOut(id));
    }

    /**
     * 根据id查询学生打卡信息
     * @param id
     * @return
     */
    @GetMapping("/stu/{id}")
    public Result selectStuAttendance(@PathVariable Integer id){
        return Result.success(stuAttendService.selectStuAttendance(id));
    }

    @GetMapping("/all/stu/{id}")
    public Result selectAllStuAttendance(@PathVariable Integer id){
        return Result.success(stuAttendService.selectAllStuAttendance(id));
    }

    @GetMapping("/class/all/stu/{id}")
    public Result selectClassAllStuAttend(@PathVariable Integer id){
        return Result.success(stuAttendService.selectClassAllStuAttend(id));
    }

    @GetMapping("/class/all/stu/today/{id}")
    public Result selectClassTodayAttend(@PathVariable Integer id){
        return Result.success(stuAttendService.selectClassTodayAttend(id));
    }

    /**
     * 员工签到
     * @param employeeAttendance
     * @return
     */
    @PostMapping("/emp")
    public Result empAttendance(@RequestBody EmployeeAttendance employeeAttendance){
        return Result.success(empAttendService.empAttendance(employeeAttendance));
    }

    /**
     * 员工签退
     * @param id
     * @return
     */
    @PostMapping("emp/out")
    public Result empCheckOut(@RequestParam("id") Integer id){
        return Result.success(empAttendService.empCheckOut(id));
    }

    @GetMapping("/emp/{id}")
    public Result selectEmpAttendance(@PathVariable Integer id){
        return Result.success(empAttendService.selectEmpAttendance(id));
    }

    @GetMapping("/all/emp/{id}")
    public Result selectAllEmpAttendance(@PathVariable Integer id){
        return Result.success(empAttendService.selectAllEmpAttendance(id));
    }

    @GetMapping("/download/emp")
    public ResponseEntity<byte[]> downloadAllEmpAttend(
            @RequestParam Integer campusId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) throws IOException {

        List<EmpAttendDownload> empAttendDownloads = empAttendService.downloadAllEmpAttend(campusId, name, startDate, endDate);
        // 使用 Excel 工具类生成 Excel 文件，直接传入 User 列表
        ByteArrayOutputStream outputStream = ExcelUtil.createExcel(empAttendDownloads, EmpAttendDownload.class, "员工签到");

        // 设置 HTTP 响应头，告知这是一个附件并命名为 users.xlsx
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=users.xlsx");

        // 返回 Excel 文件内容
        return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);
    }

    @GetMapping("/download/stu")
    public ResponseEntity<byte[]> downloadAllStuAttend(
            @RequestParam Integer campusId,
            @RequestParam(required = false) Integer classId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) throws IOException {

        List<StuAttendDownload> stuAttendDownloads = stuAttendService.downloadAllStuAttend(campusId, classId, name, startDate, endDate);
        ByteArrayOutputStream outputStream = ExcelUtil.createExcel(stuAttendDownloads, StuAttendDownload.class, "学生签到");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=students.xlsx");

        return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);
    }

}
