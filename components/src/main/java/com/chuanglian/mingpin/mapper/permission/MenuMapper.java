package com.chuanglian.mingpin.mapper.permission;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chuanglian.mingpin.entity.permission.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
@TableName("permissionManagement.menu")
public interface MenuMapper extends BaseMapper<Menu> {

    // 通过用户ID查询相应的权限信息
    List<String> selectPermsByUserID(Integer userId);
}
