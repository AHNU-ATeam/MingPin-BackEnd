package com.chuanglian.mingpin.entity.campus;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Image {
    private Integer id;
    private String url;
    private Integer noticeId;
    private Integer status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
