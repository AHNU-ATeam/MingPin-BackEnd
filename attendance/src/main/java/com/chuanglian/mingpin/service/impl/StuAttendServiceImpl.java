package com.chuanglian.mingpin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chuanglian.mingpin.entity.attendance.StudentAttendance;
import com.chuanglian.mingpin.entity.user.Student;
import com.chuanglian.mingpin.mapper.attendance.EmpAttendInfoMapper;
import com.chuanglian.mingpin.mapper.attendance.EmpAttendMapper;
import com.chuanglian.mingpin.mapper.attendance.StuAttendMapper;
import com.chuanglian.mingpin.mapper.user.StudentMapper;
import com.chuanglian.mingpin.mapper.user.TeacherMapper;
import com.chuanglian.mingpin.service.StuAttendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class StuAttendServiceImpl extends ServiceImpl<StuAttendMapper, StudentAttendance> implements StuAttendService {

    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private StuAttendMapper stuAttendMapper;

    /**
     * 定时插入打卡信息
     */
//    @Scheduled(cron = "0 0 5 * * ?")
//    public void createDailyAttendanceRecords(){
//        LocalDate today = LocalDate.now();
//
//        List<Student> students = studentMapper.selectList(null);
//        for(Student student:students){
//            StudentAttendance studentAttendance = new StudentAttendance();
//            studentAttendance.setDate(today);
//            studentAttendance.setStudentId(student.getUserId());
//            stuAttendMapper.insert(studentAttendance);
//        }
//    }
    @Scheduled(cron = "0 0 5 * * ?")
    public void createDailyAttendanceRecords() {
        LocalDate today = LocalDate.now();
        List<Student> students = studentMapper.selectList(null);

        List<StudentAttendance> attendanceList = new ArrayList<>();

        for (Student student : students) {
            StudentAttendance studentAttendance = new StudentAttendance();
            studentAttendance.setDate(today);
            studentAttendance.setStudentId(student.getUserId());
            attendanceList.add(studentAttendance);
        }

        if (!attendanceList.isEmpty()) {
            stuAttendMapper.batchInsert(attendanceList);  // Call XML batch insert
        }
    }

    @Override
    public Integer stuAttendance(Integer id, String photo) {

        Integer integer = stuAttendMapper.stuAttendance(id, LocalDate.now(), LocalTime.now(), photo);
        if(integer==0)
            throw new RuntimeException("打卡失败");
        return integer;
    }

    @Override
    public Integer stuCheckOut(Integer id) {
        Integer integer = stuAttendMapper.stuCheckOut(id, LocalDate.now(), LocalTime.now());
        if(integer==0)
            throw new RuntimeException("签退失败");
        return integer;
    }

    @Override
    public List<StudentAttendance> selectStuAttendance(Integer id) {
        LambdaQueryWrapper<StudentAttendance> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StudentAttendance::getStudentId,id);
        List<StudentAttendance> studentAttendances = stuAttendMapper.selectList(wrapper);
        return studentAttendances;
    }

    @Override
    public List<StudentAttendance> selectAllStuAttendance(Integer id) {
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Student::getCampusId,id);
        List<Student> students = studentMapper.selectList(wrapper);
        List<Integer> list = students.stream().map(Student::getUserId).toList();
        LambdaQueryWrapper<StudentAttendance> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.in(StudentAttendance::getStudentId,list);
        List<StudentAttendance> studentAttendances = stuAttendMapper.selectList(wrapper1);
        return studentAttendances;
    }

}
