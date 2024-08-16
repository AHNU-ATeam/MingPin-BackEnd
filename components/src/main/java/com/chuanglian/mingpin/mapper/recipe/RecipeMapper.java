package com.chuanglian.mingpin.mapper.recipe;

import com.chuanglian.mingpin.entity.recipe.Recipe;
import org.apache.ibatis.annotations.*;

@Mapper
public interface RecipeMapper {
    @Insert("insert into recipe(content,date,campus_id,created_at,updated_at)"+
            "values (#{content},#{date},#{campus_id},#{created_at},#{updated_at})")
    void add(Recipe recipe);

    @Delete("delete from recipe where id=#{recipeId}")
    void deleteById(Integer recipeId);

    @Update("update recipe set content = #{content}, campus_id = #{campus_id}, updated_at = #{updated_at} where recipe_id = #{recipe_id}")
    void update(Recipe recipe);

    @Select("select * from recipe where id = #{recipeId}")
    Recipe findById(Integer recipeId);
}