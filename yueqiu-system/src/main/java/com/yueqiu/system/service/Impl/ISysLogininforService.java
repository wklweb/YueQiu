package com.yueqiu.system.service.Impl;

import com.yueqiu.system.domain.SysLogininfor;
import com.yueqiu.system.mapper.LogininforMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ISysLogininforService implements com.yueqiu.system.service.ISysLogininforService {

    @Autowired
    private LogininforMapper logininforMapper;
    @Override
    public void insertLogininfor(SysLogininfor logininfor) {
        logininforMapper.insertLogininfor(logininfor);

    }

    public List<SysLogininfor> selectLogininfors(SysLogininfor sysLogininfor) {
            return logininforMapper.selectLogininforList(sysLogininfor);
    }
}
