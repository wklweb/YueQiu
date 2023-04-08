package com.yueqiu.system.service;

import com.yueqiu.common.domain.entity.SysOperLog;

public interface SysOperLogService {

    void recordOperLog(SysOperLog sysOperLog);
}
