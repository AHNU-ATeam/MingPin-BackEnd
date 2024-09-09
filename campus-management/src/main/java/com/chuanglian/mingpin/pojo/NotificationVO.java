package com.chuanglian.mingpin.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationVO {

    private Integer id;

    private String title;
    
    private String content;

    private Integer publisher;

    private String recipient;
}
