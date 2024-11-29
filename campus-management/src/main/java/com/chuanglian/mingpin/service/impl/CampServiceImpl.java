package com.chuanglian.mingpin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.chuanglian.mingpin.entity.campus.Campus;
import com.chuanglian.mingpin.entity.user.Principal;
import com.chuanglian.mingpin.entity.user.User;
import com.chuanglian.mingpin.mapper.campus.CampMapper;
import com.chuanglian.mingpin.mapper.user.PrincipalMapper;
import com.chuanglian.mingpin.mapper.user.UserMapper;
import com.chuanglian.mingpin.pojo.PageBean;
import com.chuanglian.mingpin.pojo.Result;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import com.chuanglian.mingpin.service.CampService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CampServiceImpl implements CampService {

    @Autowired
    private CampMapper campMapper;
    @Autowired
    UserMapper userMapper;
//    @Autowired
//    PrincipalMapper principalMapper;
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

//        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
//        updateWrapper.eq("id", campus.getPrincipalId()).set("nickname", campus.getPrincipalName());

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

    @Override
    public Result getCampusById(Integer campusId) {
        Campus campus = campMapper.selectById(campusId);
//        Principal principal = principalMapper.selectById(campus.getPrincipalId());
//        User user = userMapper.selectById(principal.getUserId());
        User user = userMapper.selectById(campus.getPrincipalId());
        campus.setPhone(user.getBoundPhone());
        return Result.success(campus);
    }
}
