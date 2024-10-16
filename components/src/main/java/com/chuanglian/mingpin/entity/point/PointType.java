package com.chuanglian.mingpin.entity.point;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("pointManagement.point_type")
public class PointType {
    @TableId(value = "type_id",type = IdType.AUTO)
    private Integer typeId;
    @JsonProperty("label")
    private String type;
    @JsonProperty("value")
    private Integer change;
    private Integer campusId;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updatedAt;
}