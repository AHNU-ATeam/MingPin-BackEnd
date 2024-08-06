package com.mingpin.service.impl;

import com.mingpin.mapper.CampMapper;
import com.mingpin.pojo.Campus;
import com.mingpin.service.CampService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CampServiceImpl implements CampService {

    @Autowired
    private CampMapper campMapper;
    @Override
    public void addCampus(Campus campus) {
        campMapper.insertCamp(campus);
    }
}
