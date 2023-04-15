package com.yueqiu.system.mapper;

import com.yueqiu.common.domain.entity.SysTime;

import java.util.List;

public interface SysTimeMapper {
    List<SysTime> selectTimeList(SysTime sysTime);

    SysTime selectTimeOnly(SysTime sysTime);

    int insertAreaTime(SysTime sysTime);

    int deleteTimeById(Long timeId);
}
