package com.yueqiu.framework.web.service;

import com.yueqiu.common.constant.CacheConstants;
import com.yueqiu.common.core.redis.RedisCache;
import com.yueqiu.common.domain.entity.SysUser;
import com.yueqiu.common.utils.StringUtils;
import com.yueqiu.system.mapper.SysUserMapper;
import com.yueqiu.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysResetPasswordService {

    @Autowired
    private RedisCache redisCache;
    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 检查重置密码关键字是否一致
     * @param resetKey
     * @return
     */
    public boolean checkResetKey(String resetKey) {
        String email = redisCache.getCacheObject(CacheConstants.RESET_PASSWORD_KEY+resetKey);
        if(!StringUtils.isEmpty(email)){
            return true;
        }
        else {
            return false;
        }
    }

    public int resetPassword(SysUser sysUser,String resetKey) {
        int num = sysUserMapper.updateUserInfo(sysUser);
        if(num>0){
            redisCache.deleteObject(CacheConstants.RESET_PASSWORD_KEY+resetKey);
        }
        return num;
    }
}
