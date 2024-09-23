package com.chuanglian.mingpin.entity.point;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@TableName("[pointManagement].[point_records]")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PointRecords {
    @TableId(value = "point_record_id",type = IdType.AUTO)
    private Integer pointRecordId;
    private Integer studentId;
    private Integer pointChange;
    private String type;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
}
