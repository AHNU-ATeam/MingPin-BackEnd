package com.chuanglian.mingpin.controller;

import com.chuanglian.mingpin.entity.recipe.Recipe;
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
    public Result add(@RequestBody @Validated Recipe recipe) {
        recipeService.add(recipe);
        return Result.success();
    }

    @DeleteMapping
    public Result delete(Integer recipe_id) {
        recipeService.deleteById(recipe_id);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody @Validated Recipe recipe) {
        recipeService.update(recipe);
        return Result.success();
    }

    @GetMapping
    public Result<Recipe> detail(Integer recipe_id) {
        Recipe recipe = recipeService.findById(recipe_id);
        return Result.success(recipe);
    }
}