package com.chuanglian.mingpin.service;

<<<<<<<< HEAD:login/src/main/java/com/chuanglian/mingpin/service/RecipeService.java
import com.chuanglian.mingpin.entity.Recipe;
========
import com.chuanglian.mingpin.entity.recipe.Recipe;
>>>>>>>> origin/review.huang-ln:recipe/src/main/java/com/chuanglian/mingpin/service/RecipeService.java

public interface RecipeService {

    void add(Recipe recipe);

    void deleteById(Integer recipeId);

    void update(Recipe recipe);

    Recipe findById(Integer recipeId);
}
