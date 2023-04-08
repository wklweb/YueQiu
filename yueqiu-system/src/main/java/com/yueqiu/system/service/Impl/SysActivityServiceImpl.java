package com.yueqiu.system.service.Impl;

import com.yueqiu.common.constant.UserConstants;
import com.yueqiu.common.domain.entity.SysActivity;
import com.yueqiu.system.mapper.SysActivityMapper;
import com.yueqiu.system.service.SysActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysActivityServiceImpl implements SysActivityService {
    @Autowired
    private SysActivityMapper sysActivityMapper;

    /**
     * 检查发布活动是否唯一
     * @param activityId
     * @return
     */
    @Override
    public boolean checkActivityUnique(Long activityId) {
        return sysActivityMapper.selectActivityById(activityId)!=null?UserConstants.NOT_UNIQUE:UserConstants.UNIQUE;
    }

    @Override
    public int sendNewActivity(SysActivity sysActivity) {
        return sysActivityMapper.insertActivity(sysActivity);
    }

    @Override
    public SysActivity getActivityById(Long activityId) {
        return sysActivityMapper.selectActivityById(activityId);
    }
}
