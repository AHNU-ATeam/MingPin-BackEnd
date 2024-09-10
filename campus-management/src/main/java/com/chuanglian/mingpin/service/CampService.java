package com.chuanglian.mingpin.service;


import com.chuanglian.mingpin.entity.campus.Campus;
import com.chuanglian.mingpin.pojo.PageBean;
import com.chuanglian.mingpin.pojo.Result;

import java.time.LocalDate;
import java.util.List;

public interface CampService {


    Result addCampus(Campus campus);


    PageBean page(Integer page, Integer pageSize, String name, LocalDate begin, LocalDate end);

    Result delete(Integer ids);

    void update(Campus campus);
}
