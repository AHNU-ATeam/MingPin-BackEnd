package com.chuanglian.mingpin.controller;

<<<<<<<< HEAD:login/src/main/java/com/chuanglian/mingpin/controller/RecipeController.java
import com.chuanglian.mingpin.entity.Recipe;
========
import com.chuanglian.mingpin.entity.recipe.Recipe;
>>>>>>>> origin/review.huang-ln:recipe/src/main/java/com/chuanglian/mingpin/controller/RecipeController.java
import com.chuanglian.mingpin.pojo.Result;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.chuanglian.mingpin.service.RecipeService;

@RestController
@RequestMapping("/recipe")
public class RecipeController {
    @Autowired
    private RecipeService recipeService;

    @PostMapping
    public Result add(@RequestBody Recipe recipe) {
<<<<<<<< HEAD:login/src/main/java/com/chuanglian/mingpin/controller/RecipeController.java
========
        System.out.println("OK");
        System.out.println(recipe);

>>>>>>>> origin/review.huang-ln:recipe/src/main/java/com/chuanglian/mingpin/controller/RecipeController.java
        recipeService.add(recipe);
        return Result.success();
    }

    @DeleteMapping
    public Result delete(Integer recipeId) {
        recipeService.deleteById(recipeId);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody @Validated Recipe recipe) {
        recipeService.update(recipe);
        return Result.success();
    }

    @GetMapping
    public Result<Recipe> detail(Integer recipeId) {
        Recipe recipe = recipeService.findById(recipeId);
        return Result.success(recipe);
    }
}