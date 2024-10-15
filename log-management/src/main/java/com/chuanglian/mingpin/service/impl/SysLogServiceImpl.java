package com.chuanglian.mingpin.service.impl;

import com.chuanglian.mingpin.entity.SysLog;
import com.chuanglian.mingpin.mapper.SysLogMapper;
import com.chuanglian.mingpin.service.SysLogService;
import org.springframework.stereotype.Service;

@Service
public class SysLogServiceImpl implements SysLogService {
    private final SysLogMapper sysLogMapper;

    public SysLogServiceImpl(SysLogMapper sysLogMapper) {
        this.sysLogMapper = sysLogMapper;
    }

    @Override
    public void insert(SysLog log) {
        sysLogMapper.insert(log);
    }
}
