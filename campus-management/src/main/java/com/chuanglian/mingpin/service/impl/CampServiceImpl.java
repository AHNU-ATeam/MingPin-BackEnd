package com.chuanglian.mingpin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chuanglian.mingpin.entity.campus.Campus;
import com.chuanglian.mingpin.mapper.campus.CampMapper;
import com.chuanglian.mingpin.pojo.PageBean;
import com.chuanglian.mingpin.pojo.Result;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import com.chuanglian.mingpin.service.CampService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CampServiceImpl implements CampService {

    @Autowired
    private CampMapper campMapper;
    @Override
    public Result addCampus(Campus campus) {
        if(campMapper.insertCamp(campus) ==0){
            return Result.error("添加失败");
        }
        return Result.success("添加成功");
    }

    @Override
    public PageBean page(Integer page, Integer pageSize, String name, LocalDate begin, LocalDate end) {
        //1. 设置分页参数
        PageHelper.startPage(page,pageSize);

        //2. 执行查询
        List<Campus> empList = campMapper.list(name,  begin, end);
        Page<Campus> p = (Page<Campus>) empList;

        //3. 封装PageBean对象
        PageBean pageBean = new PageBean(p.getTotal(), p.getResult());
        return pageBean;
    }

    @Override
    public Result delete(Integer id) {
        if(campMapper.delete(id) == 0){
            return Result.error("删除失败");
        }
        return Result.success("删除成功");

    }

    @Override
    public Result update(Campus campus) {
        campus.setUpdatedAt(LocalDate.now());
        campus.setIsDeleted(1);
        if(campMapper.updateById(campus) == 0){
            return Result.error("更新失败");
        }
        return Result.success("更新成功");

    }

    @Override
    public Result getAllCampus() {
        QueryWrapper<Campus> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_deleted", 1);
        // 执行查询
        List<Campus> campusList = new ArrayList<>();
        if((campusList = campMapper.selectList(queryWrapper))  == null){
            return Result.error("查询失败");
        }
        return Result.success(campusList);
    }
}
