package com.chuanglian.mingpin.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassAlbumInfoVO {
    Long id;
    String title;
    @JsonProperty("class")
    ClassInfoVO classInfo;
    PublisherVO publisher;
}




