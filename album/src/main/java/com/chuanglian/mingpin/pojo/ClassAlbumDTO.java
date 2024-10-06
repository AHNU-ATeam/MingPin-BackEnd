package com.chuanglian.mingpin.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassAlbumDTO {

    private Long classId;

    private String title;

    private ClassAlbumImageVO[] imageVOS;

    private ClassAlbumVideoVO[] videoVOS;

    private String publisher;
}
