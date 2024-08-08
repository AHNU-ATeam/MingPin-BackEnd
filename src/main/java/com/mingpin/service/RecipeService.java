package com.mingpin.service;

import com.mingpin.pojo.Recipe;

public interface RecipeService {

    void add(Recipe recipe);

    void deleteById(Integer recipeId);

    void update(Recipe recipe);

    Recipe findById(Integer recipeId);
}
