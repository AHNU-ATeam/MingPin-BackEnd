package com.chuanglian.mingpin.entity.point;

import com.baomidou.mybatisplus.annotation.*;
import com.chuanglian.mingpin.entity.user.Student;
import com.chuanglian.mingpin.entity.user.vo.StudentVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@TableName("[pointManagement].[Point]")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Point {
    @TableId(value = "point_id",type = IdType.AUTO)
    private Integer pointId;
    private Integer studentId;
    private Integer point;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
    @TableField(exist = false)
    private StudentVo student;
}
