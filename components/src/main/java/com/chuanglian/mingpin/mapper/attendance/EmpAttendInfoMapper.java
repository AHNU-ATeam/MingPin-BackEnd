package com.chuanglian.mingpin.mapper.attendance;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chuanglian.mingpin.entity.attendance.EmployeeAttendanceInfo;

import java.util.List;
import java.util.Map;

public interface EmpAttendInfoMapper extends BaseMapper<EmployeeAttendanceInfo> {
    void insertEmpAttendInfo(List<Map<String, Object>> dataList);
}
