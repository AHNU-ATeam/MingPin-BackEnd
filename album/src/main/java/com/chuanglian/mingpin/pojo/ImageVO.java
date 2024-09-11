package com.chuanglian.mingpin.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageVO {
    private Integer id;
    private Integer order;
    private String url;
}