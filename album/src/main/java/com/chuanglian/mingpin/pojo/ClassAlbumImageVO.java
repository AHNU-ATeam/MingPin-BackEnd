package com.chuanglian.mingpin.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassAlbumImageVO {
    private Long id;
    @JsonProperty("order")
    private Integer orderId;
    private String url;
}