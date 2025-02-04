package com.chuanglian.mingpin.entity.attendance;


import com.chuanglian.mingpin.entity.attendance.utils.ExcelColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpAttendDownload {

    private Integer id;  // 主键ID

    private Integer employeeId;  // 员工ID

    @ExcelColumn("姓名")
    private String name; //员工的姓名

    @ExcelColumn("电话")
    private String phone;

    @ExcelColumn("签到日期")
    private LocalDate date;  // 签到日期

    @ExcelColumn("签到时间")
    private LocalTime time;  // 签到时间

    @ExcelColumn("签退时间")
    private LocalTime checkOutTime;//签退时间

    @ExcelColumn("签退位置")
    private String location;  // 签到位置

    @ExcelColumn("签到状态")
    private String type; //签到类型,默认为0没签到,1为签到，2为签退

}
