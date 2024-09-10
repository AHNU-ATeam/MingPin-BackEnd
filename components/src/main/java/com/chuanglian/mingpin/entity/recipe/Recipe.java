package com.chuanglian.mingpin.entity.recipe;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@TableName("recipeManagement.recipe")
@ToString
public class Recipe {
    @TableId(value = "recipe_id", type = IdType.AUTO)
    private Integer recipeId;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime date;  //规定日期只能是"yyyy-MM-dd HH:mm:ss"格式
    private Integer campusId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer status;
    private Integer type;
}