package com.chuanglian.mingpin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.chuanglian.mingpin.entity.recipe.Recipe;
import com.chuanglian.mingpin.mapper.recipe.RecipeMapper;
import com.chuanglian.mingpin.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    private RecipeMapper recipeMapper;

    @Override
    public void add(Recipe recipe) {
        recipe.setCreatedAt(LocalDateTime.now());
        recipe.setUpdatedAt(LocalDateTime.now());

        recipeMapper.insert(recipe);
    }

    @Override
    public void deleteById(Integer recipeId) {
        LambdaUpdateWrapper<Recipe> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Recipe::getRecipeId, recipeId);
        wrapper.set(Recipe::getStatus, 1); // 将status设置为1表示删除
        recipeMapper.update(null, wrapper);
    }

    @Override
    public void update(Recipe recipe) {
        // 设置更新时间
        recipe.setUpdatedAt(LocalDateTime.now());

        // 创建 LambdaUpdateWrapper 实例
        LambdaUpdateWrapper<Recipe> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Recipe::getRecipeId, recipe.getRecipeId()); // 使用 id 作为唯一标识符

        // 使用 LambdaUpdateWrapper 进行更新
        recipeMapper.update(recipe, wrapper);
    }

    @Override
    public Recipe findById(Integer recipeId) {
        // 使用QueryWrapper直接排除已删除的记录
        LambdaQueryWrapper<Recipe> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Recipe::getRecipeId, recipeId).ne(Recipe::getStatus, 1); // 排除status为1的记录

        return recipeMapper.selectOne(queryWrapper);
    }

    @Override
    public List<Recipe> findByDate(String date, int type) {
        // 假设 selectByDate 方法不存在，我们需要重新定义查询逻辑
        // 这里使用LambdaQueryWrapper来构建查询条件
        LambdaQueryWrapper<Recipe> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Recipe::getType, type).eq(Recipe::getDate, date).ne(Recipe::getStatus, 1); // 排除status为1的记录

        return recipeMapper.selectList(queryWrapper);
    }
}