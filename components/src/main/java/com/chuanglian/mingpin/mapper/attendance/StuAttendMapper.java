package com.chuanglian.mingpin.mapper.attendance;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chuanglian.mingpin.entity.attendance.StudentAttendance;
import com.chuanglian.mingpin.entity.user.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface StuAttendMapper extends BaseMapper<StudentAttendance> {
    @Select("select campusManagement.class.class_id,campusManagement.class.name,campusManagement.class.num,attendanceManagement.vw_today_attendance_by_class.date,attendanceManagement.vw_today_attendance_by_class.type,attendanceManagement.vw_today_attendance_by_class.total_attendance " +
            "from campusManagement.class,attendanceManagement.vw_today_attendance_by_class where campusManagement.class.class_id=vw_today_attendance_by_class.class_id and campusManagement.class.campus_id=#{id} and vw_today_attendance_by_class.date=#{date}")
    List<Map<String, Object>> getClassAttendance(Integer id,String date);

    @Select("select * from attendanceManagement.student_attendance where date=#{date} and class_id=#{id}")
    List<StudentAttendance> getStudAttendance(Integer id, String date);

    @MapKey("studentId")
    Map<Integer, Student> getStudentsByIds(List<Integer> allStudentIds);


    Integer insertStuAttend(StudentAttendance attendance);


//    Integer stuAttendance(StudentAttendance studentAttendance);
}
