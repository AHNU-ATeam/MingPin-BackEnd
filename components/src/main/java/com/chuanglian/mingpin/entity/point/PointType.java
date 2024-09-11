package com.chuanglian.mingpin.entity.point;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}