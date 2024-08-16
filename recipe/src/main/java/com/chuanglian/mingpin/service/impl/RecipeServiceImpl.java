package com.chuanglian.mingpin.service.impl;

import com.chuanglian.mingpin.mapper.recipe.RecipeMapper;
import com.chuanglian.mingpin.entity.recipe.Recipe;
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
        recipe.setCreated_at(LocalDateTime.now());
        recipe.setUpdated_at(LocalDateTime.now());

        recipeMapper.add(recipe);
    }

    @Override
    public void deleteById(Integer recipeId) {
        recipeMapper.deleteById(recipeId);
    }

    @Override
    public void update(Recipe recipe) {
        recipe.setUpdated_at(LocalDateTime.now());
        recipeMapper.update(recipe);
    }

    @Override
    public Recipe findById(Integer recipeId) {
        return recipeMapper.findById(recipeId);
    }
}
