package com.mingpin.mapper;

import com.mingpin.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM [user] WHERE id = #{id}")
    User findByID(Integer id);

}
