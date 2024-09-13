package com.chuanglian.mingpin.entity.campus;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("campusManagement.files")
public class File {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String url;
    private Integer noticeId;
    private Integer orderId;
    private Integer status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
