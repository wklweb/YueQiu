package com.yueqiu.system.service;

import com.yueqiu.common.domain.entity.SysOperLog;
import com.yueqiu.common.domain.entity.TableInfo;

import java.util.List;

public interface SysOperLogService {

    void recordOperLog(SysOperLog sysOperLog);


    List<?> selectOperLogList(SysOperLog sysOperLog);

    int delOperLogById(Long operLogId);
}
