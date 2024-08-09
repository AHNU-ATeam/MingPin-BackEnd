package com.mingpin.mapper;

import com.mingpin.pojo.Recipe;
import org.apache.ibatis.annotations.*;


@Mapper
public interface RecipeMapper {
    @Insert("insert into recipeManagement.recipe(content,date,campus_id,created_at,updated_at)"+
            "values (#{content},#{date},#{campus_id},#{created_at},#{updated_at})")
    void add(Recipe recipe);

    @Delete("delete from recipeManagement.recipe where recipe_id=#{recipeId}")
    void deleteById(Integer recipeId);

    @Update("update recipeManagement.recipe set content = #{content}, campus_id = #{campus_id}, updated_at = #{updated_at} where recipe_id = #{recipe_id}")
    void update(Recipe recipe);

    @Select("select * from recipeManagement.recipe where recipe_id = #{recipeId}")
    Recipe findById(Integer recipeId);
}