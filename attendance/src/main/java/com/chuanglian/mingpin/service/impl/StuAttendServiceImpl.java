package com.chuanglian.mingpin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chuanglian.mingpin.entity.attendance.StuAttendDownload;
import com.chuanglian.mingpin.entity.attendance.StudentAttendance;
import com.chuanglian.mingpin.entity.user.Student;
import com.chuanglian.mingpin.entity.user.User;
import com.chuanglian.mingpin.entity.campus.Class;
import com.chuanglian.mingpin.entity.vo.AttendanceStatisticsVo;
import com.chuanglian.mingpin.entity.vo.StudentAttendanceVo;
import com.chuanglian.mingpin.mapper.attendance.StuAttendMapper;
import com.chuanglian.mingpin.mapper.campus.ClassMgmtMapper;
import com.chuanglian.mingpin.mapper.user.StudentMapper;
import com.chuanglian.mingpin.mapper.user.UserMapper;
import com.chuanglian.mingpin.service.StuAttendService;
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
public class StuAttendServiceImpl extends ServiceImpl<StuAttendMapper, StudentAttendance> implements StuAttendService {

    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private StuAttendMapper stuAttendMapper;
    @Autowired
    private ClassMgmtMapper classMgmtMapper;
    @Autowired
    private UserMapper userMapper;

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
    public List<StudentAttendanceVo> selectStuAttendance(Integer id) {
        LambdaQueryWrapper<StudentAttendance> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StudentAttendance::getStudentId,id);
        List<StudentAttendance> studentAttendances = stuAttendMapper.selectList(wrapper);
        List<StudentAttendanceVo> studentAttendanceVos = BeanUtil.copyToList(studentAttendances, StudentAttendanceVo.class);
        User user = userMapper.selectById(id);
        for (StudentAttendanceVo studentAttendanceVo:studentAttendanceVos){
            studentAttendanceVo.setName(user.getNickname());
        }
        return studentAttendanceVos;
    }

    @Override
    public List<StudentAttendanceVo> selectAllStuAttendance(Integer id) {
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Student::getCampusId,id);
        List<Student> students = studentMapper.selectList(wrapper);
        List<Integer> list = students.stream().map(Student::getUserId).toList();
        LambdaQueryWrapper<StudentAttendance> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.in(StudentAttendance::getStudentId,list);
        List<StudentAttendance> studentAttendances = stuAttendMapper.selectList(wrapper1);
        List<StudentAttendanceVo> studentAttendanceVos = BeanUtil.copyToList(studentAttendances, StudentAttendanceVo.class);
        List<User> users = userMapper.selectBatchIds(list);
        Map<Integer, String> userMap = users.stream().collect(Collectors.toMap(User::getId, User::getNickname));
        for (StudentAttendanceVo vo : studentAttendanceVos) {
            String userName = userMap.get(vo.getStudentId()); // 从 Map 中获取对应的 userName
            if (userName != null) {
                vo.setName(userName); // 设置 vo 的 name
            }
        }
        return studentAttendanceVos;
    }

    @Override
    public List<StudentAttendanceVo> selectClassAllStuAttend(Integer id) {
        List<Integer> studentsId = classMgmtMapper.getStudentsId(id);
        LambdaQueryWrapper<StudentAttendance> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.in(StudentAttendance::getStudentId,studentsId)
                .orderByDesc(StudentAttendance::getDate);
        List<StudentAttendance> studentAttendances = stuAttendMapper.selectList(wrapper1);

        List<StudentAttendanceVo> studentAttendanceVos = BeanUtil.copyToList(studentAttendances, StudentAttendanceVo.class);
        List<User> users = userMapper.selectBatchIds(studentsId);
        Map<Integer, String> userMap = users.stream().collect(Collectors.toMap(User::getId, User::getNickname));
        for (StudentAttendanceVo vo : studentAttendanceVos) {
            String userName = userMap.get(vo.getStudentId()); // 从 Map 中获取对应的 userName
            if (userName != null) {
                vo.setName(userName); // 设置 vo 的 name
            }
        }


        return studentAttendanceVos;
    }

    @Override
    public List<StudentAttendanceVo> selectClassTodayAttend(Integer id) {
        List<Integer> studentsId = classMgmtMapper.getStudentsId(id);
        LambdaQueryWrapper<StudentAttendance> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.in(StudentAttendance::getStudentId,studentsId)
                .eq(StudentAttendance::getDate,LocalDate.now());
        List<StudentAttendance> studentAttendances = stuAttendMapper.selectList(wrapper1);

        List<StudentAttendanceVo> studentAttendanceVos = BeanUtil.copyToList(studentAttendances, StudentAttendanceVo.class);
        List<User> users = userMapper.selectBatchIds(studentsId);
        Map<Integer, String> userMap = users.stream().collect(Collectors.toMap(User::getId, User::getNickname));
        for (StudentAttendanceVo vo : studentAttendanceVos) {
            String userName = userMap.get(vo.getStudentId()); // 从 Map 中获取对应的 userName
            if (userName != null) {
                vo.setName(userName); // 设置 vo 的 name
            }
        }
        return studentAttendanceVos;
    }

    @Override
    public List<StuAttendDownload> downloadAllStuAttend(Integer campusId, Integer classId, String name, LocalDate startDate, LocalDate endDate) {
        // 第一步：根据校区ID查询所有班级ID
        LambdaQueryWrapper<Class> classWrapper = new LambdaQueryWrapper<>();
        classWrapper.eq(Class::getCampusId, campusId);
        if (classId != null) {
            classWrapper.eq(Class::getId, classId);
        }
        List<Class> classes = classMgmtMapper.selectList(classWrapper);
        List<Integer> classIds = classes.stream().map(Class::getId).toList();

        // 第二步：根据班级ID查询学生的 user_id 和班级名
        List<StuAttendDownload> studentInfo = stuAttendMapper.findStudentInfoByClassIds(classIds);

        // 使用 Map 存储学生ID和班级名称的对应关系
        Map<Integer, String> studentClassMap = studentInfo.stream()
                .collect(Collectors.toMap(StuAttendDownload::getStudentId, StuAttendDownload::getClassName));
        /// 提取学生ID
        List<Integer> userIds = studentInfo.stream().map(StuAttendDownload::getStudentId).collect(Collectors.toList());

        // 第三步：根据过滤条件（姓名，日期范围）查询考勤记录
        List<StuAttendDownload> attendanceByFilters = stuAttendMapper.findAttendanceByFilters(userIds, name, startDate, endDate);

        // 第四步：将班级名称添加到考勤记录中
        for (StuAttendDownload attendance : attendanceByFilters) {
            // 使用 Map 快速获取班级名称
            String className = studentClassMap.get(attendance.getStudentId());
            attendance.setClassName(className); // 设置班级名称
        }
        return attendanceByFilters;
    }

    @Override
    public List<Integer> getStudentsIsAttended(LocalDate date) {
        LocalDateTime start = LocalDateTime.of(date, LocalTime.MIN);
        LocalDateTime end = LocalDateTime.of(date, LocalTime.MAX);
        QueryWrapper<StudentAttendance> studentsIsAttendedQueryWrapper = new QueryWrapper<>();
        studentsIsAttendedQueryWrapper
                .select("student_id")
                .ge("time", start)
                .le("time", end);

        return stuAttendMapper.selectObjs(studentsIsAttendedQueryWrapper)
                .stream()
                .map(obj -> (Integer) obj)
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentAttendanceVo> selectAllStuTodayAttendance(Integer id) {
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Student::getCampusId,id);
        List<Student> students = studentMapper.selectList(wrapper);
        List<Integer> list = students.stream().map(Student::getUserId).toList();
        LambdaQueryWrapper<StudentAttendance> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.in(StudentAttendance::getStudentId,list)
                .eq(StudentAttendance::getDate,LocalDate.now());
        List<StudentAttendance> studentAttendances = stuAttendMapper.selectList(wrapper1);
        List<StudentAttendanceVo> studentAttendanceVos = BeanUtil.copyToList(studentAttendances, StudentAttendanceVo.class);
        List<User> users = userMapper.selectBatchIds(list);
        Map<Integer, String> userMap = users.stream().collect(Collectors.toMap(User::getId, User::getNickname));
        for (StudentAttendanceVo vo : studentAttendanceVos) {
            String userName = userMap.get(vo.getStudentId()); // 从 Map 中获取对应的 userName
            if (userName != null) {
                vo.setName(userName); // 设置 vo 的 name
            }
        }
        return studentAttendanceVos;
    }

    public AttendanceStatisticsVo getTodayAttendanceStatistics(Integer campusId) {

        // 调用原有方法获取所有学生的今日打卡记录
        List<StudentAttendanceVo> studentAttendanceVos = selectAllStuTodayAttendance(campusId);

        // 统计已签到、仅签到未签退、已签退和未签到的学生数量
        long notCheckedInCount = studentAttendanceVos.stream()
                .filter(vo -> vo.getType() == 0)
                .count();
        long checkedInCount = studentAttendanceVos.stream()
                .filter(vo -> vo.getType() == 1)
                .count();
        long checkedOutCount = studentAttendanceVos.stream()
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
