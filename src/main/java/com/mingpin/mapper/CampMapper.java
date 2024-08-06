package com.mingpin.mapper;


import com.mingpin.pojo.Campus;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CampMapper {

    @Insert("insert into campus (logo, name, address, principal_id, renewal_start,renewal_end,info,population, created_at,updated_at) values " +
            "#{logo},#{name},#{address},#{principalId},#{renewalStart},#{renewalEnd},#{info},#{population},#{createdAt},#{updatedAt}")
    void insertCamp(Campus campus);
}
