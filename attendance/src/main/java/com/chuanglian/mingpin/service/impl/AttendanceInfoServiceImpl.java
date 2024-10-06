package com.chuanglian.mingpin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chuanglian.mingpin.entity.attendance.EmployeeAttendanceInfo;
import com.chuanglian.mingpin.mapper.attendance.EmpAttendInfoMapper;
import com.chuanglian.mingpin.service.AttendanceInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttendanceInfoServiceImpl implements AttendanceInfoService {
    @Autowired
    private EmpAttendInfoMapper empAttendInfoMapper;
    @Override
    public EmployeeAttendanceInfo getEmpInfo(Integer id) {
        LambdaQueryWrapper<EmployeeAttendanceInfo> wrapper = new LambdaQueryWrapper<EmployeeAttendanceInfo>().eq(EmployeeAttendanceInfo::getCampusId,id);
        EmployeeAttendanceInfo employeeAttendanceInfo = empAttendInfoMapper.selectOne(wrapper);
        if(employeeAttendanceInfo==null){
            throw new RuntimeException("没有校区打卡设置信息");
        }
        return employeeAttendanceInfo;
    }

    @Override
    public int updateEmpInfo(EmployeeAttendanceInfo employeeAttendanceInfo) {
//        employeeAttendanceInfo.setUpdatedAt(LocalDateTime.now());

        int result = empAttendInfoMapper.updateById(employeeAttendanceInfo);
        if(result == 0)
            throw new RuntimeException("更改失败");
        return result;
    }

    public int addEmpInfo(Integer id){
        EmployeeAttendanceInfo employeeAttendanceInfo = new EmployeeAttendanceInfo();
        employeeAttendanceInfo.setCampusId(id);
        int insert = empAttendInfoMapper.insert(employeeAttendanceInfo);
        return insert;
    }
}
