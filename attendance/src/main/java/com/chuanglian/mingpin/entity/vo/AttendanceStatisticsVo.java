package com.chuanglian.mingpin.entity.vo;

import lombok.Data;

@Data
public class AttendanceStatisticsVo {
    private Integer notCheckedInCount=0; // 未签到学生数
    private Integer checkedInCount=0; // 已签到学生数
    private Integer checkedOutCount=0; // 以前退学生数
}
