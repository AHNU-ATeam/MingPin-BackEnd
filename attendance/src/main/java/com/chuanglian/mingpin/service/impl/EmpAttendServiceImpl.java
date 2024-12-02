package com.chuanglian.mingpin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chuanglian.mingpin.entity.attendance.EmpAttendDownload;
import com.chuanglian.mingpin.entity.attendance.EmployeeAttendance;
import com.chuanglian.mingpin.entity.attendance.EmployeeAttendanceInfo;
import com.chuanglian.mingpin.entity.attendance.StudentAttendance;
import com.chuanglian.mingpin.entity.user.Teacher;
import com.chuanglian.mingpin.entity.user.User;
import com.chuanglian.mingpin.entity.vo.AttendanceStatisticsVo;
import com.chuanglian.mingpin.entity.vo.EmployeeAttendanceVo;
import com.chuanglian.mingpin.entity.vo.StudentAttendanceVo;
import com.chuanglian.mingpin.mapper.attendance.EmpAttendInfoMapper;
import com.chuanglian.mingpin.mapper.attendance.EmpAttendMapper;
import com.chuanglian.mingpin.mapper.user.TeacherMapper;
import com.chuanglian.mingpin.mapper.user.UserMapper;
import com.chuanglian.mingpin.service.EmpAttendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EmpAttendServiceImpl extends ServiceImpl<EmpAttendMapper, EmployeeAttendance> implements EmpAttendService {
    @Autowired
    private EmpAttendInfoMapper empAttendInfoMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private EmpAttendMapper empAttendMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * 定时插入打卡信息
     */
//    @Scheduled(cron = "0 0 5 * * ?")
//    public void createDailyAttendanceRecords(){
//        LocalDate today = LocalDate.now();
//        List<EmployeeAttendanceInfo> campusList = empAttendInfoMapper.selectList(null);
//
//
//        for (EmployeeAttendanceInfo campus : campusList) {
//            // 获取该校区的所有员工
//            LambdaQueryWrapper<Teacher> wrapper = new LambdaQueryWrapper<>();
//            wrapper.eq(Teacher::getCampusId,campus.getCampusId());
//            List<Teacher> teachers = teacherMapper.selectList(wrapper);
//            for(Teacher teacher : teachers){
//                EmployeeAttendance employeeAttendance = new EmployeeAttendance();
//                employeeAttendance.setEmployeeId(teacher.getUserId());
//                employeeAttendance.setDate(today);
//                empAttendMapper.insert(employeeAttendance);
//            }
//        }
//    }
    @Scheduled(cron = "0 0 5 * * ?")
    public void createDailyAttendanceRecords() {
        LocalDate today = LocalDate.now();
        List<EmployeeAttendanceInfo> campusList = empAttendInfoMapper.selectList(null);

        // 定义一个列表来存储所有要插入的考勤记录
        List<EmployeeAttendance> attendanceList = new ArrayList<>();

        for (EmployeeAttendanceInfo campus : campusList) {
            // 获取该校区的所有员工
            LambdaQueryWrapper<Teacher> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Teacher::getCampusId, campus.getCampusId())
                    .eq(Teacher::getStatus,1);
            List<Teacher> teachers = teacherMapper.selectList(wrapper);

            // 将每个员工的考勤记录添加到列表中
            for (Teacher teacher : teachers) {
                EmployeeAttendance employeeAttendance = new EmployeeAttendance();
                employeeAttendance.setEmployeeId(teacher.getUserId());
                employeeAttendance.setDate(today);
                attendanceList.add(employeeAttendance);
            }
        }

        // 批量插入考勤记录
        if (!attendanceList.isEmpty()) {
            empAttendMapper.insertBatch(attendanceList);
        }
    }

    @Override
    public int empAttendance(EmployeeAttendance employeeAttendance) {
        LocalDate date = LocalDate.now();
        employeeAttendance.setTime(LocalTime.now());
        employeeAttendance.setType(1);
        LambdaQueryWrapper<EmployeeAttendance> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EmployeeAttendance::getEmployeeId,employeeAttendance.getEmployeeId())
                .eq(EmployeeAttendance::getDate,date);
        int update = empAttendMapper.update(employeeAttendance, wrapper);
        return update;
    }

    @Override
    public int empCheckOut(Integer id) {
        LocalDate date = LocalDate.now();
        LambdaQueryWrapper<EmployeeAttendance> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EmployeeAttendance::getEmployeeId,id)
                .eq(EmployeeAttendance::getDate,date);
        EmployeeAttendance employeeAttendance = new EmployeeAttendance();
        employeeAttendance.setCheckOutTime(LocalTime.now());
        employeeAttendance.setType(2);
        int update = empAttendMapper.update(employeeAttendance, wrapper);
        return update;
    }

    @Override
    public List<EmployeeAttendanceVo> selectEmpAttendance(Integer id) {
        LambdaQueryWrapper<EmployeeAttendance> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EmployeeAttendance::getEmployeeId,id)
                .orderByDesc(EmployeeAttendance::getDate);
        List<EmployeeAttendance> employeeAttendances = empAttendMapper.selectList(wrapper);
        User user = userMapper.selectById(id);
        List<EmployeeAttendanceVo> employeeAttendanceVos = BeanUtil.copyToList(employeeAttendances, EmployeeAttendanceVo.class);
        for(EmployeeAttendanceVo employeeAttendanceVo:employeeAttendanceVos){
            employeeAttendanceVo.setName(user.getNickname());
        }
        return employeeAttendanceVos;
    }

    @Override
    public List<EmployeeAttendanceVo> selectAllEmpAttendance(Integer id) {
        LambdaQueryWrapper<Teacher> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Teacher::getCampusId,id);
        List<Teacher> teachers = teacherMapper.selectList(wrapper);
        List<Integer> list = teachers.stream().map(Teacher::getUserId).toList();
        LambdaQueryWrapper<EmployeeAttendance> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.in(EmployeeAttendance::getEmployeeId,list);
        List<EmployeeAttendance> employeeAttendances = empAttendMapper.selectList(wrapper1);
        List<EmployeeAttendanceVo> employeeAttendanceVos = BeanUtil.copyToList(employeeAttendances, EmployeeAttendanceVo.class);
        List<User> users = userMapper.selectBatchIds(list);
        Map<Integer, String> userMap = users.stream().collect(Collectors.toMap(User::getId, User::getNickname));
        for (EmployeeAttendanceVo vo : employeeAttendanceVos) {
            String userName = userMap.get(vo.getEmployeeId()); // 从 Map 中获取对应的 userName
            if (userName != null) {
                vo.setName(userName); // 设置 vo 的 name
            }
        }
        return employeeAttendanceVos;
    }

    @Override
    public List<EmpAttendDownload> downloadAllEmpAttend(Integer campusId, String name, LocalDate startDate, LocalDate endDate) {
        // 第一步：根据校区 ID 查询教师的 userId
//        List<Integer> userIds = empAttendMapper.findUserIdsByCampusId(campusId);
        LambdaQueryWrapper<Teacher> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Teacher::getCampusId,campusId);
        List<Teacher> teachers = teacherMapper.selectList(wrapper);
        List<Integer> userIds = teachers.stream().map(Teacher::getUserId).toList();

        // 第二步：根据过滤条件查询考勤记录
        List<EmpAttendDownload> attendanceByFilters = empAttendMapper.findAttendanceByFilters(userIds, name, startDate, endDate);
        return attendanceByFilters;
    }

    @Override
    public List<Integer> getEmployeeIsAttended(LocalDate date) {
        LocalDateTime start = LocalDateTime.of(date, LocalTime.MIN);
        LocalDateTime end = LocalDateTime.of(date, LocalTime.MAX);
        QueryWrapper<EmployeeAttendance> studentsIsAttendedQueryWrapper = new QueryWrapper<>();
        studentsIsAttendedQueryWrapper
                .select("student_id")
                .ge("time", start)
                .le("time", end);

        return empAttendMapper.selectObjs(studentsIsAttendedQueryWrapper)
                .stream()
                .map(obj -> (Integer) obj)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeAttendanceVo> selectAllEmpTodayAttendance(Integer id) {
        LambdaQueryWrapper<Teacher> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Teacher::getCampusId,id);
        List<Teacher> teachers = teacherMapper.selectList(wrapper);
        List<Integer> list = teachers.stream().map(Teacher::getUserId).toList();
        LambdaQueryWrapper<EmployeeAttendance> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.in(EmployeeAttendance::getEmployeeId,list)
                .eq(EmployeeAttendance::getDate,LocalDate.now());
        List<EmployeeAttendance> employeeAttendances = empAttendMapper.selectList(wrapper1);
        List<EmployeeAttendanceVo> employeeAttendanceVos = BeanUtil.copyToList(employeeAttendances, EmployeeAttendanceVo.class);
        List<User> users = userMapper.selectBatchIds(list);
        Map<Integer, String> userMap = users.stream().collect(Collectors.toMap(User::getId, User::getNickname));
        for (EmployeeAttendanceVo vo : employeeAttendanceVos) {
            String userName = userMap.get(vo.getEmployeeId()); // 从 Map 中获取对应的 userName
            if (userName != null) {
                vo.setName(userName); // 设置 vo 的 name
            }
        }
        return employeeAttendanceVos;
    }

    @Override
    public AttendanceStatisticsVo getTodayEmpAttendanceStatistics(Integer id) {
        // 调用原有方法获取所有学生的今日打卡记录
        List<EmployeeAttendanceVo> empAttendanceVos = selectAllEmpTodayAttendance(id);


        // 统计已签到、仅签到未签退、已签退和未签到的学生数量
        long notCheckedInCount = empAttendanceVos.stream()
                .filter(vo -> vo.getType() == 0)
                .count();
        long checkedInCount = empAttendanceVos.stream()
                .filter(vo -> vo.getType() == 1)
                .count();
        long checkedOutCount = empAttendanceVos.stream()
                .filter(vo -> vo.getType() == 2)
                .count();


        // 创建并填充 AttendanceStatisticsVo
        AttendanceStatisticsVo statisticsVo = new AttendanceStatisticsVo();
        statisticsVo.setNotCheckedInCount((int) notCheckedInCount);
        statisticsVo.setCheckedInCount((int) checkedInCount);
        statisticsVo.setCheckedOutCount((int) checkedOutCount);

        return statisticsVo;
    }

}
