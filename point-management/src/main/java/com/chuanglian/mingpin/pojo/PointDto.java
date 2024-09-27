package com.chuanglian.mingpin.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PointDto {
    @TableId(value = "point_id",type = IdType.AUTO)
    private Integer pointId;
    //userId
    private Integer studentId;
    private Integer point;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private StudentInPointVo student;
}
