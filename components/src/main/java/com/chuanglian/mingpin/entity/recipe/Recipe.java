package com.chuanglian.mingpin.entity.recipe;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {
    private int recipe_id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;  //规定日期只能是"yyyy-MM-dd HH:mm:ss"格式
    private int campus_id;
    private String content;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}