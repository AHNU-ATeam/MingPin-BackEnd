package com.chuanglian.mingpin.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {
    private int recipeId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;  //规定日期只能是"yyyy-MM-dd HH:mm:ss"格式
    private int campusId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}