package com.yueqiu.common.core.redis;

import com.yueqiu.common.core.domain.model.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

@Component
public class RedisCache {
    @Autowired
    public RedisTemplate redisTemplate;


    public <T> T getCacheObject(final String key)
    {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }


    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key 缓存的键值
     * @param value 缓存的值
     * @param timeout 时间
     * @param timeUnit 时间颗粒度
     */
    public <T> void setCacheObject(final String key, final T value, final Integer timeout, final TimeUnit timeUnit)
    {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    /**
     * 删除单个对象
     *
     * @param key
     */
    public boolean deleteObject(final String key)
    {
        return redisTemplate.delete(key);
    }

    /**
     * 设置没有过期时间的配置缓存
     * @param configKey
     * @param configValue
     */
    public void setCacheObject(String configKey, String configValue) {
        redisTemplate.opsForValue().set(configKey, configValue);
    }

    public boolean hasKey(String retryCountKey) {
        return redisTemplate.hasKey(retryCountKey);
    }

    public Collection<String> getKeys(String keys) {
        return redisTemplate.keys(keys);
    }
}
