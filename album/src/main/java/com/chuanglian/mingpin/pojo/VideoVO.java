package com.chuanglian.mingpin.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoVO {
    private Integer id;
    private Integer order;
    private String url;
}