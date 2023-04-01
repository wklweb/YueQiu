package com.yueqiu.framework.web.service;
import com.yueqiu.common.constant.CacheConstants;
import com.yueqiu.common.constant.Constants;
import com.yueqiu.common.core.redis.RedisCache;
import com.yueqiu.common.domain.entity.SysUser;
import com.yueqiu.common.exception.user.UserPasswordNotMatchException;
import com.yueqiu.common.exception.user.UserPasswordRetryLimitExceedException;
import com.yueqiu.common.utils.MessageUtils;
import com.yueqiu.common.utils.SecurityUtils;
import com.yueqiu.common.utils.StringUtils;
import com.yueqiu.framework.manager.AsyncFactory;
import com.yueqiu.framework.manager.AsyncManager;
import com.yueqiu.framework.security.context.AuthenticationContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class SysPasswordService {
    @Autowired
    private RedisCache redisCache;
    @Value("${user.password.maxRetryCount}")
    private int maxRetryCount;
    @Value(("${user.password.lockTime}"))
    private int lockTime;

    public void validate(SysUser sysUser){
        Authentication authentication = AuthenticationContextHolder.getContext();
        String loginName = authentication.getName();
        String password = authentication.getCredentials().toString();
        //错误次数验证
        Integer retryCount = redisCache.getCacheObject(getCacheKey(loginName));
        if(retryCount==null){
            retryCount=0;
        }
        //验证有无超过次数
        if(retryCount>=Integer.valueOf(maxRetryCount).intValue()){
            AsyncManager.me().execute(AsyncFactory.recordLoginInfo(loginName, Constants.LOGIN_FAIL,
                    MessageUtils.getMessage("user.password.retry.limit.exceed", maxRetryCount, lockTime)));
            throw new UserPasswordRetryLimitExceedException(maxRetryCount,lockTime);
        }
        //密码匹配
        if(!match(sysUser,password)){
            retryCount = retryCount+1;
            AsyncManager.me().execute(AsyncFactory.recordLoginInfo(loginName, Constants.LOGIN_FAIL,
                    MessageUtils.getMessage("user.password.retry.limit.count",retryCount)));
            redisCache.setCacheObject(getCacheKey(loginName),retryCount,lockTime, TimeUnit.MINUTES);
            throw new UserPasswordNotMatchException();
        }
        else {
            clearRetryCount(getCacheKey(loginName));
        }

    }

    private void clearRetryCount(String retryCountKey) {
        if(redisCache.hasKey(retryCountKey)){
            redisCache.deleteObject(retryCountKey);
        }
    }

    private boolean match(SysUser sysUser,String password) {
        return SecurityUtils.matchesPassword(password,sysUser.getPassword());
    }

    private String getCacheKey(String loginName) {
        return CacheConstants.PWD_ERR_CNT_KEY + loginName;
    }
}
