package com.chuanglian.mingpin.pojo;

import com.chuanglian.mingpin.entity.user.vo.StudentVO;
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
    private StudentVO student;
    private LocalDateTime createdAt;
}