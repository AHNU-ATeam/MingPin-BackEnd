package com.chuanglian.mingpin.controller;

import com.chuanglian.mingpin.entity.recipe.Recipe;
import com.chuanglian.mingpin.pojo.Result;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.chuanglian.mingpin.service.RecipeService;

import java.util.List;

@RestController
@RequestMapping("/recipe")
public class RecipeController {
    @Autowired
    private RecipeService recipeService;

    @PostMapping
    @PreAuthorize("hasAuthority('sys:recipe:add')")
    public Result add(@RequestBody Recipe recipe) {
        recipe.setRecipeId(null);
        recipeService.add(recipe);
        return Result.success();
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('sys:recipe:delete')")
    public Result delete(Integer recipeId) {
        recipeService.deleteById(recipeId);
        return Result.success();
    }

    @PutMapping
    @PreAuthorize("hasAuthority('sys:recipe:update')")
    public Result update(@RequestBody @Validated Recipe recipe) {
        recipeService.update(recipe);
        return Result.success();
    }

    @GetMapping
    @PreAuthorize("hasAuthority('sys:recipe:select')")
    public Result<Recipe> detail(Integer recipeId) {
        Recipe recipe = recipeService.findById(recipeId);
        return Result.success(recipe);
    }

    //根据日期查询食谱
    @GetMapping("/date")
    @PreAuthorize("hasAuthority('sys:recipe:selectByDate')")
    public Result<List<Recipe>> findByDate(@RequestParam String date) {
        List<Recipe> recipeList = recipeService.findByDate(date);
        return Result.success(recipeList);
    }
}