package com.mingpin.mapper;

import com.mingpin.pojo.StudentRankingDTO;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface IntegralMapper {

    @Select("SELECT student_id, student_name, points " +
            "FROM userManagement.student " +
            "WHERE class = #{className} " +
            "ORDER BY points DESC")
    List<StudentRankingDTO> getStudentRankingInClass(@Param("className") String className);

    @Update("UPDATE userManagement.student SET points = points + #{points} WHERE student_id = #{studentId}")
    void addPoints(Long studentId, Integer points);

    @Select("SELECT student_id, student_name, points " +
            "FROM userManagement.student " +
            "WHERE grade = #{gradeName} " +
            "ORDER BY points DESC")
    List<StudentRankingDTO> getStudentRankingInGrade(String gradeName);

    @Select("SELECT SUM(points_change) FROM userManagement.Integral_history WHERE student_id = #{studentId} AND change_date BETWEEN #{startDate} AND #{endDate}")
    int getPointsByDateRange(@Param("studentId") Long studentId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Insert("INSERT INTO userManagement.Integral_history (student_id, points_change) VALUES (#{studentId}, #{points})")
    void insertPointsHistory(@Param("studentId") Long studentId, @Param("points") int points);


}
