package com.chuanglian.mingpin.service;

import com.chuanglian.mingpin.entity.recipe.Recipe;

import java.util.List;

public interface RecipeService {

    void add(Recipe recipe);

    void deleteById(Integer recipeId);

    void update(Recipe recipe);

    Recipe findById(Integer recipeId);

    List<Recipe> findByDate(String date);
}
