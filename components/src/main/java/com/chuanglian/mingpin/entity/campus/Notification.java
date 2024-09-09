package com.chuanglian.mingpin.entity.campus;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    private Integer id;
    
    private String title;
    
    private String content;
    
    private String imageUrl;
    
    private String fileUrl;

    private Integer publisher;

    private String recipient;
    
    private int status;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
}
