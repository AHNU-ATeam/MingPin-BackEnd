package com.chuanglian.mingpin.mapper;

import com.chuanglian.mingpin.entity.Recipe;
import org.apache.ibatis.annotations.*;


@Mapper
public interface RecipeMapper {
    @Insert("insert into recipeManagement.recipe(content,date,campus_id,created_at,updated_at)"+
            "values (#{content},#{date},#{campusId},#{createdAt},#{updatedAt})")
    void add(Recipe recipe);

    @Delete("delete from recipeManagement.recipe where recipe_id=#{recipeId}")
    void deleteById(Integer recipeId);

    @Update("update recipeManagement.recipe set content = #{content}, campus_id = #{campusId}, updated_at = #{updatedAt} where recipe_id = #{recipeId}")
    void update(Recipe recipe);

    @Select("select * from recipeManagement.recipe where recipe_id = #{recipeId}")
    Recipe findById(Integer recipeId);
}