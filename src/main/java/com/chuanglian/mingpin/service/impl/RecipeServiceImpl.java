package com.chuanglian.mingpin.service.impl;

import com.chuanglian.mingpin.mapper.RecipeMapper;
import com.chuanglian.mingpin.entity.Recipe;
import com.chuanglian.mingpin.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RecipeServiceImpl implements RecipeService {
    @Autowired
    private RecipeMapper recipeMapper;

    @Override
    public void add(Recipe recipe) {
        recipe.setCreatedAt(LocalDateTime.now());
        recipe.setUpdatedAt(LocalDateTime.now());

        recipeMapper.add(recipe);
    }

    @Override
    public void deleteById(Integer recipeId) {
        recipeMapper.deleteById(recipeId);
    }

    @Override
    public void update(Recipe recipe) {
        recipe.setUpdatedAt(LocalDateTime.now());
        recipeMapper.update(recipe);
    }

    @Override
    public Recipe findById(Integer recipeId) {
        Recipe recipe = recipeMapper.findById(recipeId);
        return recipe;
    }
}
