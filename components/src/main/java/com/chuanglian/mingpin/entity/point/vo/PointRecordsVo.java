package com.chuanglian.mingpin.entity.point.vo;

import com.baomidou.mybatisplus.annotation.*;
import com.chuanglian.mingpin.entity.user.vo.StudentVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PointRecordsVo {
    private Integer pointRecordId;
    private Integer studentId;
    private Integer pointChange;
    private String type;
    private StudentVo student;
    private LocalDateTime createdAt;
}