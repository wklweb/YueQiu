package com.yueqiu.system.service;

import com.yueqiu.common.domain.entity.SysTime;

import java.util.List;

public interface SysTimeService {

    List<SysTime> selectTimeList(SysTime sysTime);

    String checkSysTime(SysTime sysTime);

    int addAreaTime(SysTime sysTime);

    int removeAreaTime(Long timeId);
}
