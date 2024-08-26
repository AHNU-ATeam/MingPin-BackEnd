package com.chuanglian.mingpin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chuanglian.mingpin.entity.attendance.EmployeeAttendance;
import com.chuanglian.mingpin.entity.attendance.EmployeeAttendanceInfo;
import com.chuanglian.mingpin.entity.attendance.StudentAttendance;
import com.chuanglian.mingpin.entity.campus.ClassStudent;
import com.chuanglian.mingpin.entity.user.Student;
import com.chuanglian.mingpin.entity.user.Teacher;
import com.chuanglian.mingpin.mapper.attendance.EmpAttendInfoMapper;
import com.chuanglian.mingpin.mapper.attendance.EmpAttendMapper;
import com.chuanglian.mingpin.mapper.attendance.StuAttendMapper;
import com.chuanglian.mingpin.mapper.campus.ClassStudentMapper;
import com.chuanglian.mingpin.mapper.user.TeacherMapper;
import com.chuanglian.mingpin.service.AttendanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@EnableScheduling
public class AttendanceServiceImpl implements AttendanceService {
    @Autowired
    private EmpAttendMapper empAttendMapper;
    @Autowired
    private StuAttendMapper stuAttendMapper;
    @Autowired
    private ClassStudentMapper classStudentMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private EmpAttendInfoMapper empAttendInfoMapper;

    @Override
    public List<Map<String, Object>> getClassAttendance(Integer id,String date) {
        System.out.println(id+date);
        return stuAttendMapper.getClassAttendance(id,date);
    }

    @Override
    public Map<Integer, List<StudentAttendance>> getStudAttendance(Integer classId, String date) {
        // 获取考勤记录
        List<StudentAttendance> studAttendances = stuAttendMapper.getStudAttendance(classId, date);

        // 提取所有学生 ID
        List<Integer> allStudentIds = new ArrayList<>();
        for (StudentAttendance studentAttendance : studAttendances) {
            allStudentIds.add(studentAttendance.getStudentId());
        }

        System.out.println(allStudentIds);
        // 查询所有学生信息
        Map<Integer, Student> students = stuAttendMapper.getStudentsByIds(allStudentIds);

        // 将学生信息设置到考勤记录中
        for (StudentAttendance studentAttendance : studAttendances) {
            Student student = students.get(studentAttendance.getStudentId());
            studentAttendance.setStudent(student);
        }

        // 按 type 分组考勤记录
        Map<Integer, List<StudentAttendance>> groupedAttendances = new HashMap<>();
        for (StudentAttendance studentAttendance : studAttendances) {
            Integer type = studentAttendance.getType();
            groupedAttendances.computeIfAbsent(type, k -> new ArrayList<>()).add(studentAttendance);
        }

        return groupedAttendances;
    }

    @Override
    public Integer stuAttendance(StudentAttendance studentAttendance) {
        studentAttendance.setUpdatedAt(LocalDateTime.now());
        studentAttendance.setType(1);
        LambdaQueryWrapper<StudentAttendance> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StudentAttendance::getClassId,studentAttendance.getClassId())
                        .eq(StudentAttendance::getStudentId,studentAttendance.getStudentId())
                                .eq(StudentAttendance::getDate,studentAttendance.getDate());

        return stuAttendMapper.update(studentAttendance,wrapper);
    }

    @Override
    public Integer stuUnattendance(StudentAttendance studentAttendance) {
        studentAttendance.setUpdatedAt(LocalDateTime.now());
        studentAttendance.setType(0);
        LambdaQueryWrapper<StudentAttendance> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StudentAttendance::getClassId,studentAttendance.getClassId())
                .eq(StudentAttendance::getStudentId,studentAttendance.getStudentId())
                .eq(StudentAttendance::getDate,studentAttendance.getDate());
        return stuAttendMapper.update(studentAttendance,wrapper);
    }

    @Override
    public Integer stuAskForLeave(StudentAttendance studentAttendance) {
        studentAttendance.setUpdatedAt(LocalDateTime.now());
        studentAttendance.setType(2);
        LambdaQueryWrapper<StudentAttendance> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StudentAttendance::getClassId,studentAttendance.getClassId())
                .eq(StudentAttendance::getStudentId,studentAttendance.getStudentId())
                .eq(StudentAttendance::getDate,studentAttendance.getDate());
        return stuAttendMapper.update(studentAttendance,wrapper);
    }

    @Override
    public Integer StuSignOut(StudentAttendance studentAttendance) {
        studentAttendance.setUpdatedAt(LocalDateTime.now());
        studentAttendance.setSignOut(1);
        LambdaQueryWrapper<StudentAttendance> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StudentAttendance::getClassId,studentAttendance.getClassId())
                .eq(StudentAttendance::getStudentId,studentAttendance.getStudentId())
                .eq(StudentAttendance::getDate,studentAttendance.getDate());
        return stuAttendMapper.update(studentAttendance,wrapper);
    }

    @Override
    public List<EmployeeAttendance> getTeaAttendance(Integer id, String date) {
        LambdaQueryWrapper<EmployeeAttendance> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(EmployeeAttendance::getCampus_id,id)
                .eq(EmployeeAttendance::getDate,date);
        List<EmployeeAttendance> employeeAttendances = empAttendMapper.selectList(wrapper);
        System.out.println("hello");
        // 为每个考勤记录设置对应的教师信息
        for (EmployeeAttendance attendance : employeeAttendances) {
            // 根据员工ID查询教师信息
            Teacher teacher = teacherMapper.selectById(attendance.getEmployeeId());
            attendance.setTeacher(teacher);
        }
        return employeeAttendances;
    }

    @Override
    public Integer setTeaAttendance(Integer id, Integer type, String location, String photo) {
        System.out.println(id);
        Teacher teacher = teacherMapper.selectById(id);
        System.out.println(teacher);
        LambdaQueryWrapper<EmployeeAttendanceInfo> infoWrapper = new LambdaQueryWrapper<>();
        infoWrapper.eq(EmployeeAttendanceInfo::getCampusId,teacher.getCampusId());
        List<EmployeeAttendanceInfo> employeeAttendanceInfos = empAttendInfoMapper.selectList(infoWrapper);
        EmployeeAttendanceInfo employeeAttendanceInfo = employeeAttendanceInfos.get(0);
        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now();

        System.out.println(localDate);

        LocalDateTime localDateTime = LocalDateTime.now();
        if(type==1){
            if(LocalTime.now().isAfter(employeeAttendanceInfo.getStartTime())&&LocalTime.now().isBefore(employeeAttendanceInfo.getEndTime())){
                return empAttendMapper.setTeaAttendance1(id, localDate, localTime,localDateTime,location, photo);
            }
        } else if (type==2) {
            if(LocalTime.now().isAfter(employeeAttendanceInfo.getStartTime2())&&LocalTime.now().isBefore(employeeAttendanceInfo.getEndTime2())){
                return empAttendMapper.setTeaAttendance2(id, localDate, localTime,localDateTime,location, photo);
            }
        } else if (type==3) {
            if(LocalTime.now().isAfter(employeeAttendanceInfo.getStartTime3())&&LocalTime.now().isBefore(employeeAttendanceInfo.getEndTime3())){
                return empAttendMapper.setTeaAttendance3(id, localDate, localTime,localDateTime,location, photo);
            }
        } else if (type==4) {
            if(LocalTime.now().isAfter(employeeAttendanceInfo.getStartTime4())&&LocalTime.now().isBefore(employeeAttendanceInfo.getEndTime4())){
                return empAttendMapper.setTeaAttendance4(id, localDate, localTime,localDateTime,location, photo);
            }
        }
        return 0;

    }


    @Scheduled(cron = "15 40 13 * * ?")  // 每天0点执行任务
    void insertDailyAttendance() {
        log.info("定时创建签到表");
        // 获取所有班级学生信息
        List<ClassStudent> classStudents = classStudentMapper.selectList(null);

        // 遍历每个学生，插入考勤记录
        for (ClassStudent classStudent : classStudents) {
            StudentAttendance attendance = new StudentAttendance();
            attendance.setId(5);
            attendance.setStudentId(classStudent.getStudentId());
            attendance.setAttendanceId(0);
            attendance.setDate(LocalDate.now());
            attendance.setTime(LocalTime.now());
            attendance.setType(0);  // 设置实际的 type
            attendance.setClassId(classStudent.getClassId());
//            attendance.setLocation(null);  // 可选字段，可以设置为 null 或空
//            attendance.setPhoto(null);  // 可选字段，可以设置为 null 或空
            attendance.setSignOut(0);  // 设置实际的 signOut
            attendance.setUpdatedAt(LocalDateTime.now());
            attendance.setCreatedAt(LocalDateTime.now());
            // 插入考勤记录
            stuAttendMapper.insertStuAttend(attendance);
        }
    }

    @Scheduled(cron = "15 08 14 * * ?")  // 每天0点执行任务
    void insertDailyTeacherAttendance() {
        log.info("定时创建教师签到记录");

        // 获取所有教师信息
        List<Teacher> teachers = teacherMapper.selectList(null);

        // 遍历每个教师，插入考勤记录
        for (Teacher teacher : teachers) {
            EmployeeAttendance attendance = new EmployeeAttendance();
            attendance.setEmployeeId(teacher.getTeacherId());
            attendance.setAttendanceId(0);  // 替换为实际的 attendanceId
            attendance.setDate(LocalDate.now());
            attendance.setOnTime(0);  // 初始值，实际应用中可以根据需要调整
            attendance.setOnTime2(0);  // 初始值
            attendance.setOnTime3(0);  // 初始值
            attendance.setOnTime4(0);  // 初始值
            attendance.setCreatedAt(LocalDateTime.now());
            attendance.setUpdatedAt(LocalDateTime.now());
            attendance.setCampus_id(teacher.getCampusId());

            // 插入考勤记录
            empAttendMapper.insertempAttend(attendance);
        }
    }


}
