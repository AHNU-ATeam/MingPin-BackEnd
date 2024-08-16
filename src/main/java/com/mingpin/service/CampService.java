package com.mingpin.service;


import com.mingpin.pojo.Campus;
import com.mingpin.pojo.PageBean;

import java.time.LocalDate;
import java.util.List;

public interface CampService {


    void addCampus(Campus campus);


    PageBean page(Integer page, Integer pageSize, String name, LocalDate begin, LocalDate end);

    void delete(List<Integer> ids);

    void update(Campus campus);
}
