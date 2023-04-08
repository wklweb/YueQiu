package com.yueqiu.system.service;

import com.yueqiu.common.domain.entity.SysActivity;

public interface SysActivityService {
    boolean checkActivityUnique(Long activityId);

    int sendNewActivity(SysActivity sysActivity);

    SysActivity getActivityById(Long activityId);
}
