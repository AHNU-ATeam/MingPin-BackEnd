package com.chuanglian.mingpin.entity.album;

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
@TableName("albumManagement.images")
public class Image {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    private String url;
    
    private Long albumId;
    
    private Integer orderId;
    
    private Integer status;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
}
