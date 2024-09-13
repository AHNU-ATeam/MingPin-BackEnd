package com.chuanglian.mingpin.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassAlbumVO {

    private Long id;

    private Long classId;

    private String className;

    private ClassAlbumImageVO[] images;

    private ClassAlbumVideoVO[] video;

    private String publisher;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
