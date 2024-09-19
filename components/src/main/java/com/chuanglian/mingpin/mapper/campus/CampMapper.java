package com.chuanglian.mingpin.mapper.campus;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chuanglian.mingpin.entity.campus.Campus;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface CampMapper extends BaseMapper<Campus> {

    // 新建校区
    @Insert("insert into campusManagement.campus (logo, name, address, principal_id, renewal_start,renewal_end,info,population, created_at,updated_at,campus_pics_urls,teacher_pics_urls) values " +
            "(#{logo},#{name},#{address},#{principalId},#{renewalStart},#{renewalEnd},#{info},#{population},#{createdAt},#{updatedAt},#{campusPicsUrls},#{teacherPicsUrls})")
    int insertCamp(Campus campus);

    // 查询校区
    public  List<Campus> list(String name, LocalDate begin, LocalDate end);

    // 批量删除校区(软删除)
    @Update("update campusManagement.campus set is_deleted = 1 where campus_id = #{id}")
    int delete(Integer id);


    void update(Campus campus);
}
