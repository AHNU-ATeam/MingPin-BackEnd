package com.chuanglian.mingpin.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationVO {

    private Integer id;

    private String title;
    
    private String content;

    private ImageVO[] images;

    private FileVO[] files;

    private String publisher;

    private String recipient;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
