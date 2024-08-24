package com.chuanglian.mingpin.mapper.attendance;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chuanglian.mingpin.entity.attendance.StudentAttendanceInfo;

import java.util.List;
import java.util.Map;

public interface StuAttendInfoMapper extends BaseMapper<StudentAttendanceInfo> {
    void insertStuAttendInfo(List<Map<String, Object>> dataList);

}
