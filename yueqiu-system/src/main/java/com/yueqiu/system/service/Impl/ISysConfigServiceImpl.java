package com.yueqiu.system.service.Impl;

import com.yueqiu.common.constant.CacheConstants;
import com.yueqiu.common.constant.Constants;
import com.yueqiu.common.core.redis.RedisCache;
import com.yueqiu.common.core.text.Convert;
import com.yueqiu.common.domain.entity.SysConfig;
import com.yueqiu.common.utils.StringUtils;
import com.yueqiu.system.mapper.SysConfigMapper;
import com.yueqiu.system.service.ISysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ISysConfigServiceImpl implements ISysConfigService {

    @Autowired
    private SysConfigMapper configMapper;
    @Autowired
    private RedisCache redisCache;
    @Override
    public boolean getCaptchaEnabled() {
       String captchaEnabled = selectConfigByKey("sys.account.captchaEnabled");
       if(captchaEnabled==null){
           return true;
       }
       return Convert.toBool(captchaEnabled);
    }

    public String selectConfigByKey(String key) {
        String configValue = Convert.toStr(redisCache.getCacheObject(getConfigKey(key)));
        if(StringUtils.isNotNull(configValue)){
            //更新缓存
            SysConfig sysConfig = new SysConfig();
            sysConfig.setConfigKey(key);
            SysConfig newConfig = configMapper.selectConfig(sysConfig);
            if(!configValue.equals(newConfig.getConfigValue())){
                redisCache.setCacheObject(getConfigKey(key),newConfig.getConfigValue());
                return newConfig.getConfigValue();
            }
            return configValue;
        }
        SysConfig sysConfig = new SysConfig();
        sysConfig.setConfigKey(key);
        SysConfig newSysConfig = configMapper.selectConfig(sysConfig);
        if(StringUtils.isNotNull(newSysConfig)){
            redisCache.setCacheObject(getConfigKey(key),newSysConfig.getConfigValue());
            return newSysConfig.getConfigValue();
        }
        return StringUtils.EMPTY;
    }

    private String getConfigKey(String key) {
        return CacheConstants.SYS_CONFIG_KEY + key;
    }
}
