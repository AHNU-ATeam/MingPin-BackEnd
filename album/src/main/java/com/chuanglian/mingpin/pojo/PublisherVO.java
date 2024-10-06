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
public class PublisherVO {
    @JsonProperty("publisher_id")
    int id;
    @JsonProperty("publisher_name")
    String nickname;
}