package com.chuanglian.mingpin.pojo;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassAlbumVO {

    private Long id;

    @JsonProperty("class_id")
    private Long classId;

    @JsonProperty("class_name")
    private String className;

    @JsonProperty("images")
    private ClassAlbumImageVO[] imageVOS;

    @JsonProperty("video")
    private ClassAlbumVideoVO[] videoVOS;

    private String publisher;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

}
