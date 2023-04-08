package com.yueqiu.system.mapper;

import com.yueqiu.common.domain.entity.SysActivity;

public interface SysActivityMapper {
    SysActivity selectActivityById(Long activityId);

    int insertActivity(SysActivity sysActivity);
}
