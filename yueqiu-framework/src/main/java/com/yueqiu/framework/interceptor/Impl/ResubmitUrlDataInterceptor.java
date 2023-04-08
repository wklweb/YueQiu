package com.yueqiu.framework.interceptor.Impl;

import com.alibaba.fastjson.JSON;
import com.yueqiu.common.annotation.Resubmit;
import com.yueqiu.common.core.redis.RedisCache;
import com.yueqiu.common.filter.RepeatRequestWrapper;
import com.yueqiu.common.utils.HttpUtils;
import com.yueqiu.common.utils.StringUtils;
import com.yueqiu.framework.interceptor.ResubmitInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.ServletRequestWrapper;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
public class ResubmitUrlDataInterceptor extends ResubmitInterceptor {

    public static final String PARAMS = "params";
    public static final String INTERVALTIME = "intervalTime";
    public static final String PARAMS_KEY = "params_key:";
    @Value("${token.header}")
    private String header;


    @Autowired
    private RedisCache redisCache;
    /**
     * 判断是否为重复提交操作
     * @param request
     * @param resubmit
     * @return
     */
    @Override
    public boolean isResubmit(HttpServletRequest request, Resubmit resubmit) {
        String params = "";
        Map<String,Object> map = new HashMap<>();
        if(request instanceof RepeatRequestWrapper){
            RepeatRequestWrapper requestWrapper = (RepeatRequestWrapper) request;
            params = HttpUtils.getBodyString(requestWrapper);
        }
        if(StringUtils.isEmpty(params)){
            params = JSON.toJSONString(request.getParameterMap());
        }
        map.put(PARAMS,params);
        map.put(INTERVALTIME,System.currentTimeMillis());

        //拼装缓存key
        String url = request.getRequestURI();
        String token = StringUtils.trimToEmpty(request.getHeader(header));
        String params_cache_key = PARAMS_KEY + url + token;

        Object requestInfoMap = redisCache.getCacheObject(params_cache_key);
        if(requestInfoMap!=null){
            //强转得到一个装有url对应的map参数集合
            Map<String,Object> urlRequestParams = (Map<String, Object>) requestInfoMap;
            if(isSameRequestParam(urlRequestParams.get(url),map)&&isExceedIntervalTime(urlRequestParams.get(url),map,resubmit.interval())){
                return true;
            }

        }
        else {
            Map<String,Object> cacheRequestParamMap = new HashMap<>();
            cacheRequestParamMap.put(url,map);
            redisCache.setCacheObject(params_cache_key,cacheRequestParamMap,resubmit.interval(), TimeUnit.MILLISECONDS);
        }
        return false;
    }

    /**
     * 是否超过了间隔时间
     * @param o
     * @param map
     * @return true(超过)
     */
    private boolean isExceedIntervalTime(Object o, Map<String, Object> map,int expirationTime) {
        Map<String,Object> beforeRequestParam = (Map<String, Object>) o;
        Long beforeRequestTime = (Long) beforeRequestParam.get(INTERVALTIME);
        Long newRequestTime = (Long) beforeRequestParam.get(INTERVALTIME);
        if((beforeRequestTime-newRequestTime)<expirationTime){
            return true;
        }
        return false;
    }

    /**
     * 是否提交重复的参数
     * @param o
     * @param map
     * @return true(是) false(否)
     */
    private boolean isSameRequestParam(Object o, Map<String, Object> map) {
        Map<String,Object> beforeRequestParam = (Map<String, Object>) o;
        String beforeParam = (String) beforeRequestParam.get(PARAMS);
        String newParam = (String) map.get(PARAMS);
        return beforeParam.equals(newParam);
    }

}
