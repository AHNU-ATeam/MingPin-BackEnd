package com.chuanglian.mingpin.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("images")
    private ImageVO[] imageVOs;

    @JsonProperty("files")
    private FileVO[] filesVOs;

    private String publisher;

    private String recipient;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
