package com.mingpin.service;

import com.mingpin.pojo.StudentRankingDTO;
import org.springframework.stereotype.Service;

import java.util.List;


public interface IntegralService {
    public List<StudentRankingDTO> getStudentRankingInClass(String className);

    void addPoints(Long studentId, Integer points);

    List<StudentRankingDTO> getStudentRankingInGrade(String gradeName);

    int getWeeklyPoints(Long studentId);

    int getMonthlyPoints(Long studentId);

    int getAnnualPoints(Long studentId, int year);
}
