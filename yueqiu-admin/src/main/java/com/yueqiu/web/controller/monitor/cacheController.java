package com.yueqiu.web.controller.monitor;

import com.yueqiu.common.constant.CacheConstants;
import com.yueqiu.common.constant.UserConstants;
import com.yueqiu.common.core.redis.RedisCache;
import com.yueqiu.common.domain.AjaxResult;
import com.yueqiu.common.utils.StringUtils;
import com.yueqiu.system.domain.SysCache;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

@RestController
@Api(value = "缓存监控", tags = "缓存监控")
@RequestMapping("/monitor/cache")
public class cacheController {
    private final static List<SysCache> cacheList = new ArrayList<>();

    {
        cacheList.add(new SysCache(CacheConstants.LOGIN_TOKEN_KEY,"用户休息"));
        cacheList.add(new SysCache(CacheConstants.PWD_ERR_CNT_KEY,"密码尝试次数"));
        cacheList.add(new SysCache(CacheConstants.SYS_CONFIG_KEY,"系统配置信息"));
    }


    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private RedisCache redisCache;

    @GetMapping("/list")
    @PreAuthorize("@permission.hasPerms('monitor:cache:list')")
    @ApiOperation(value = "redis监控", notes = "redis监控")
    public AjaxResult cacheMonitor(){
        Properties properties = (Properties)redisTemplate.execute((RedisCallback<Properties>) connection -> connection.info());
        Object obj = redisTemplate.execute((RedisCallback<Object>) connection -> connection.dbSize());
        Properties commandStats = (Properties) redisTemplate.execute((RedisCallback<Object>) connection -> connection.info("commandstats"));
        Map<String,Object> map = new HashMap<>(3);

        List<Map<String,String>> list = new ArrayList<>();
        commandStats.stringPropertyNames().forEach(key->{
            Map<String,String> m = new HashMap<>(3);
            m.put("name",StringUtils.removeStart(key,"cmdstat_"));
            String property = commandStats.getProperty(key);
            m.put("value",StringUtils.substringBetween(property,"calls=",",usec"));
            list.add(m);
        });



        map.put("info",properties);
        map.put("dbsize",obj);
        map.put("commandStats",list);
        return AjaxResult.success(map);
    }




    @PreAuthorize("@permission.hasPerms('monitor:cache:list')")
    @ApiOperation(value = "缓存监控", notes = "缓存监控")
    @GetMapping("/getNames")
    public AjaxResult cacheList(){
        return AjaxResult.success(cacheList);
    }
    @PreAuthorize("@permission.hasPerms('monitor:cache:list')")
    @ApiOperation(value = "根据缓存名获取缓存key", notes = "根据缓存名获取缓存key")
    @GetMapping("/getNames/{cacheName}")
    public AjaxResult getCacheKeys(@PathVariable String cacheName){
        Collection<String> collection = redisCache.getKeys(cacheName+":"+"*");
        return AjaxResult.success(collection);
    }
    @PreAuthorize("@permission.hasPerms('monitor:cache:list')")
    @ApiOperation(value = "根据缓存名+key获取缓存值", notes = "根据缓存名+key获取缓存值")
    @GetMapping("/getNames/{cacheName}/{key}")
    public AjaxResult getCacheValues(@PathVariable String cacheName,@PathVariable String key){
        String cacheKey = cacheName+":"+key;
        String cacheValue = redisTemplate.opsForValue().get(cacheKey);
        SysCache sysCache = new SysCache(cacheValue,key,cacheName);
        return AjaxResult.success(sysCache);
    }
}
