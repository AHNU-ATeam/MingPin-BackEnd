package com.chuanglian.mingpin.service.impl;

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
    public void delete(List<Integer> ids) {
        campMapper.delete(ids);
    }

    @Override
    public void update(Campus campus) {
        campus.setUpdatedAt(LocalDate.now());

        campMapper.update(campus);
    }
}
