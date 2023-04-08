package com.yueqiu.system.service.Impl;

import com.yueqiu.common.domain.entity.SysOperLog;
import com.yueqiu.system.mapper.SysOperLogMapper;
import com.yueqiu.system.service.SysOperLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysOperLogServiceImpl implements SysOperLogService {
    @Autowired
    private SysOperLogMapper sysOperLogMapper;

    /**
     * 记录操作日志
     * @param sysOperLog
     */
    @Override
    public void recordOperLog(SysOperLog sysOperLog) {
        sysOperLogMapper.insertOperLog(sysOperLog);
    }
}
