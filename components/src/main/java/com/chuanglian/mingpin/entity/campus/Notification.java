package com.chuanglian.mingpin.entity.campus;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("campusManagement.notifications")
public class Notification {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    
    private String title;
    
    private String content;

    private Integer publisher;

    private String recipient;
    
    private int status;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
}
