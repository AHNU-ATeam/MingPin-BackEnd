package com.chuanglian.mingpin.mapper.attendance;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chuanglian.mingpin.entity.attendance.EmployeeAttendanceInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface EmpAttendInfoMapper extends BaseMapper<EmployeeAttendanceInfo> {
    void insertEmpAttendInfo(List<Map<String, Object>> dataList);
}
