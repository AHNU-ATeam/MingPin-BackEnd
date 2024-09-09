package com.chuanglian.mingpin.controller;

import com.chuanglian.mingpin.entity.recipe.Recipe;
import com.chuanglian.mingpin.pojo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.chuanglian.mingpin.service.RecipeService;

import java.util.List;

@RestController
@RequestMapping("/recipe")
@Api(tags = "食谱管理")
public class RecipeController {
    @Autowired
    private RecipeService recipeService;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('sys:recipe:add')")
    @ApiOperation(value = "通过id添加食谱", notes = "该接口主要用于添加食谱信息")
    public Result add(@RequestBody Recipe recipe) {
        recipe.setRecipeId(null);
        recipeService.add(recipe);
        return Result.success();
    }

    @PostMapping("/delete/{recipeId}")
    @PreAuthorize("hasAuthority('sys:recipe:delete')")
    @ApiOperation(value = "通过id删除食谱", notes = "该接口主要用于删除食谱信息")
    public Result delete(@PathVariable Integer recipeId) {
        recipeService.deleteById(recipeId);
        return Result.success();
    }

    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sys:recipe:update')")
    @ApiOperation(value = "通过id修改食谱", notes = "该接口主要用于修改食谱信息")
    public Result update(@RequestBody @Validated Recipe recipe) {
        recipeService.update(recipe);
        return Result.success();
    }

    @GetMapping("/{recipeId}")
    @PreAuthorize("hasAuthority('sys:recipe:select')")
    @ApiOperation(value = "通过id查询食谱", notes = "该接口主要用于通过食谱id查找食谱信息")
    public Result<Recipe> detail(@PathVariable Integer recipeId) {
        Recipe recipe = recipeService.findById(recipeId);
        return Result.success(recipe);
    }

    //根据日期查询食谱
    @GetMapping("/date")
    @PreAuthorize("hasAuthority('sys:recipe:selectByDate')")
    @ApiOperation(value = "通过日期查找食谱", notes = "该接口主要用于通过日期查找食谱信息")
    public Result<List<Recipe>> findByDate(@RequestParam String date) {
        List<Recipe> recipeList = recipeService.findByDate(date);
        return Result.success(recipeList);
    }
}