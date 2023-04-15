package com.yueqiu.system.mapper;

import com.yueqiu.common.domain.entity.SysOperLog;

import java.util.List;

public interface SysOperLogMapper {
    void insertOperLog(SysOperLog sysOperLog);

    List<?> selectOperLogList(SysOperLog sysOperLog);

    int deleteOperLogById(Long operLogId);
}
