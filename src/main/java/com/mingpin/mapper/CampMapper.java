package com.mingpin.mapper;


import com.mingpin.pojo.Campus;
import com.mingpin.pojo.Result;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface CampMapper {

//    新建校区
    @Insert("insert into campusManagement.campus (logo, name, address, principal_id, renewal_start,renewal_end,info,population, created_at,updated_at) values " +
            "(#{logo},#{name},#{address},#{principalId},#{renewalStart},#{renewalEnd},#{info},#{population},#{createdAt},#{updatedAt})")
    void insertCamp(Campus campus);

    // 查询校区
    public  List<Campus> list(String name, LocalDate begin, LocalDate end);

    // 批量删除校区
    void delete(List<Integer> ids);


    void update(Campus campus);
}
