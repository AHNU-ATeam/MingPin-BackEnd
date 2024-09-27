//package com.chuanglian.mingpin.service.impl;
//
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.chuanglian.mingpin.entity.attendance.EmployeeAttendanceInfo;
//import com.chuanglian.mingpin.entity.attendance.StudentAttendanceInfo;
//import com.chuanglian.mingpin.mapper.attendance.EmpAttendInfoMapper;
//import com.chuanglian.mingpin.mapper.attendance.StuAttendInfoMapper;
//import com.chuanglian.mingpin.service.AttendanceInfoService;
//import org.springframework.aop.scope.ScopedProxyUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Service
//public class AttendanceInfoServiceImpl implements AttendanceInfoService {
//    @Autowired
//    private EmpAttendInfoMapper empAttendInfoMapper;
//    @Autowired
//    private StuAttendInfoMapper stuAttendInfoMapper;
//    @Override
//    public List<EmployeeAttendanceInfo> getEmpInfo(Integer id) {
//        LambdaQueryWrapper<EmployeeAttendanceInfo> wrapper = new LambdaQueryWrapper<EmployeeAttendanceInfo>().eq(EmployeeAttendanceInfo::getCampusId,id);
//        int num = empAttendInfoMapper.selectList(wrapper).size();
//        if(num==0){
//            addEmpAttendInfo(id);
//        }
//        return empAttendInfoMapper.selectList(wrapper);
//    }
//
//    @Override
//    public int updateEmpInfo(EmployeeAttendanceInfo employeeAttendanceInfo) {
//        employeeAttendanceInfo.setUpdatedAt(LocalDateTime.now());
//        return empAttendInfoMapper.updateById(employeeAttendanceInfo);
//    }
//
//    @Override
//    public List<StudentAttendanceInfo> getStuInfo(Integer id) {
//        System.out.println("22222");
//        LambdaQueryWrapper<StudentAttendanceInfo> wrapper = new LambdaQueryWrapper<StudentAttendanceInfo>().eq(StudentAttendanceInfo::getCampusId,id);
//        int num = stuAttendInfoMapper.selectList(wrapper).size();
//        System.out.println(num);
//        if(num==0){
//            addStuAttendInfo(id);
//            System.out.println("数据库没有");
//        }
//        return stuAttendInfoMapper.selectList(wrapper);
//    }
//
//    @Override
//    public int updateStuInfo(StudentAttendanceInfo studentAttendanceInfo) {
//        studentAttendanceInfo.setUpdatedAt(LocalDateTime.now());
//        return stuAttendInfoMapper.updateById(studentAttendanceInfo);
//    }
//
//
//    public void addEmpAttendInfo(int campusId) {
//        List<Map<String, Object>> dataList = new ArrayList<>();
//
//        Map<String, Object> data = new HashMap<>();
//        data.put("campus_id", campusId);
//
//        // 设置第一个打卡时间段
//        data.put("start_time", "08:00:45");
//        data.put("end_time", "08:20:01");
//
//        // 设置第二个打卡时间段
//        data.put("start_time_2", "11:00:50");
//        data.put("end_time_2", "11:25:27");
//
//        // 设置第三个打卡时间段
//        data.put("start_time_3", "14:00:07");
//        data.put("end_time_3", "14:20:31");
//
//        // 设置第四个打卡时间段
//        data.put("start_time_4", "19:00:01");
//        data.put("end_time_4", "19:20:36");
//
//        // 根据实际情况设置 type 值
//        data.put("type", 0);  // 1表示需要两次上下班
//
//        dataList.add(data);
//        System.out.println(dataList);
//
//        empAttendInfoMapper.insertEmpAttendInfo(dataList);
//    }
//
//
//
//    public void addStuAttendInfo(int campus_id) {
//        List<Map<String, Object>> dataList = new ArrayList<>();
//
//        Map<String, Object> data1 = new HashMap<>();
////        data1.put("attendance_id", 1);
//        data1.put("start_time", "05:00:22");
//        data1.put("end_time", "10:59:49");
//        data1.put("campus_id", campus_id);
//
////        data1.put("created_at", "2024-08-23 00:31:21.140");
////        data1.put("updated_at", "2024-08-23 00:31:21.140");
//        dataList.add(data1);
//
//        Map<String, Object> data2 = new HashMap<>();
////        data2.put("attendance_id", 2);
//        data2.put("start_time", "11:00:25");
//        data2.put("end_time", "16:00:34");
//        data2.put("campus_id", campus_id);
//        data2.put("type", 2);
////        data2.put("created_at", "2024-08-23 00:31:51.397");
////        data2.put("updated_at", "2024-08-23 00:31:51.397");
//        dataList.add(data2);
//
//        Map<String, Object> data3 = new HashMap<>();
////        data3.put("attendance_id", 3);
//        data3.put("start_time", "16:10:29");
//        data3.put("end_time", "23:59:39");
//        data3.put("campus_id", campus_id);
//        data3.put("type", 3);
////        data3.put("created_at", "2024-08-23 00:33:58.357");
////        data3.put("updated_at", "2024-08-23 00:33:58.357");
//        dataList.add(data3);
//        System.out.println(dataList);
//
//        stuAttendInfoMapper.insertStuAttendInfo(dataList);
//    }
//
//}
