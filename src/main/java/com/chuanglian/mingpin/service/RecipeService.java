package com.chuanglian.mingpin.service;

import com.chuanglian.mingpin.pojo.Recipe;

public interface RecipeService {

    void add(Recipe recipe);

    void deleteById(Integer recipeId);

    void update(Recipe recipe);

    Recipe findById(Integer recipeId);
}
