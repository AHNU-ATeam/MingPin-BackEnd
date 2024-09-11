package com.chuanglian.mingpin.pojo;


import com.chuanglian.mingpin.entity.album.Video;
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

    private ImageVO[] imageVOS;

    private VideoVO[] videoVOS;

    private String publisher;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
