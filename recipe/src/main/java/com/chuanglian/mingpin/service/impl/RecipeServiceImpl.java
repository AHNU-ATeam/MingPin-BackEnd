package com.chuanglian.mingpin.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.chuanglian.mingpin.mapper.recipe.RecipeMapper;
import com.chuanglian.mingpin.entity.recipe.Recipe;
import com.chuanglian.mingpin.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {
    @Autowired
    private RecipeMapper recipeMapper;

    @Override
    public void add(Recipe recipe) {
        System.out.println(recipe);

        recipe.setCreatedAt(LocalDateTime.now());
        recipe.setUpdatedAt(LocalDateTime.now());

        recipeMapper.insert(recipe);
    }

    @Override
    public void deleteById(Integer recipeId) {
        recipeMapper.deleteById(recipeId);
    }

    @Override
    public void update(Recipe recipe) {
        // 设置更新时间
        recipe.setUpdatedAt(LocalDateTime.now());

        // 创建 UpdateWrapper 实例
        UpdateWrapper<Recipe> wrapper = new UpdateWrapper<>();

        // 可以根据需要添加更多条件
        wrapper.eq("recipe_id", recipe.getRecipeId()); // 假设使用 id 作为唯一标识符

        // 使用 UpdateWrapper 进行更新
        recipeMapper.update(recipe, wrapper);
    }

    @Override
    public Recipe findById(Integer recipeId) {
        return recipeMapper.selectById(recipeId);
    }

    @Override
    public List<Recipe> findByDate(String date) {
        return recipeMapper.selectByDate(date);
    }
}
