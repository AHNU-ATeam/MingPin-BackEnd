package com.chuanglian.mingpin.controller;

import com.chuanglian.mingpin.entity.recipe.Recipe;
import com.chuanglian.mingpin.pojo.Result;
import jakarta.annotation.PreDestroy;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('sys:recipe:add')")
    public Result add(@RequestBody @Validated Recipe recipe) {
        recipeService.add(recipe);
        return Result.success();
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('sys:recipe:add')")
    public Result delete(Integer recipe_id) {
        recipeService.deleteById(recipe_id);
        return Result.success();
    }

    @PutMapping
    @PreAuthorize("hasAuthority('sys:recipe:add')")
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