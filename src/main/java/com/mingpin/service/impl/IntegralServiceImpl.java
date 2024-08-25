package com.mingpin.service.impl;

import com.mingpin.mapper.IntegralMapper;
import com.mingpin.pojo.StudentRankingDTO;
import com.mingpin.service.IntegralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class IntegralServiceImpl implements IntegralService {

    @Autowired
    private IntegralMapper integralMapper;

    @Override
    public List<StudentRankingDTO> getStudentRankingInClass(String className) {
        return integralMapper.getStudentRankingInClass(className);
    }

    @Override
    public void addPoints(Long studentId, Integer points) {
        integralMapper.addPoints(studentId,points);
        integralMapper.insertPointsHistory(studentId, points);  // 记录积分变动
    }

    @Override
    public List<StudentRankingDTO> getStudentRankingInGrade(String gradeName) {
        return integralMapper.getStudentRankingInGrade(gradeName);
    }

    @Override
    public int getWeeklyPoints(Long studentId) {
        return integralMapper.getPointsByDateRange(studentId, LocalDate.now().minusWeeks(1), LocalDate.now());
    }

    @Override
    public int getMonthlyPoints(Long studentId) {
        return integralMapper.getPointsByDateRange(studentId, LocalDate.now().minusMonths(1), LocalDate.now());
    }

    @Override
    public int getAnnualPoints(Long studentId, int year) {
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year, 12, 31);
        return integralMapper.getPointsByDateRange(studentId, startDate, endDate);
    }
}
