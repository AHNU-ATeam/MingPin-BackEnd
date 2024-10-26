package com.chuanglian.mingpin.mapper.attendance;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chuanglian.mingpin.entity.attendance.EmpAttendDownload;
import com.chuanglian.mingpin.entity.attendance.EmployeeAttendance;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Mapper
public interface EmpAttendMapper extends BaseMapper<EmployeeAttendance> {
    @Update("update attendanceManagement.employee_attendance set time=#{localTime},location=#{location},photo=#{photo},updated_at=#{localDateTime},on_time=1 where date=#{localDate} and employee_id=#{id}")
    Integer setTeaAttendance1(Integer id, LocalDate localDate, LocalTime localTime, LocalDateTime localDateTime, String location, String photo);

    @Update("update attendanceManagement.employee_attendance set time2=#{localTime},location2=#{location},photo2=#{photo},updated_at=#{localDateTime},on_time2=1 where date=#{localDate} and employee_id=#{id}")
    Integer setTeaAttendance2(Integer id, LocalDate localDate, LocalTime localTime, LocalDateTime localDateTime, String location, String photo);

    @Update("update attendanceManagement.employee_attendance set time3=#{localTime},location3=#{location},photo3=#{photo},updated_at=#{localDateTime},on_time3=1 where date=#{localDate} and employee_id=#{id}")
    Integer setTeaAttendance3(Integer id, LocalDate localDate, LocalTime localTime, LocalDateTime localDateTime, String location, String photo);

    @Update("update attendanceManagement.employee_attendance set time4=#{localTime},location4=#{location},photo4=#{photo},updated_at=#{localDateTime},on_time4=1 where date=#{localDate} and employee_id=#{id}")
    Integer setTeaAttendance4(Integer id, LocalDate localDate, LocalTime localTime, LocalDateTime localDateTime, String location, String photo);

    Integer insertempAttend(EmployeeAttendance attendance);

    void insertBatch(List<EmployeeAttendance> attendanceList);


    List<EmpAttendDownload> findAttendanceByFilters(@Param("userIds") List<Integer> userIds,
                                                    @Param("name") String name,
                                                    @Param("startDate") LocalDate startDate,
                                                    @Param("endDate") LocalDate endDate);
}
