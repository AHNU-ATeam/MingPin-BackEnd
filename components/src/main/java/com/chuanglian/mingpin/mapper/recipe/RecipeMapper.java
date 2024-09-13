package com.chuanglian.mingpin.mapper.recipe;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chuanglian.mingpin.entity.recipe.Recipe;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@TableName("recipeManagement.recipe")
public interface RecipeMapper extends BaseMapper<Recipe> {

}
