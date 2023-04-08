package com.yueqiu.framework.aspectj;

import com.alibaba.fastjson2.JSON;
import com.yueqiu.common.annotation.Log;
import com.yueqiu.common.core.domain.model.LoginUser;
import com.yueqiu.common.domain.entity.SysOperLog;
import com.yueqiu.common.enums.BusinessStatus;
import com.yueqiu.common.filter.PropertyPreExcludeFilter;
import com.yueqiu.common.utils.*;
import com.yueqiu.framework.manager.AsyncFactory;
import com.yueqiu.framework.manager.AsyncManager;
import io.swagger.models.HttpMethod;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NamedThreadLocal;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Map;

/**
 * AOP记录操作日志
 */
@Aspect//注解该类为切面拓展类
@Component
public class LogAspect {
    public static final Logger log = LoggerFactory.getLogger(Logger.class);
    private static final String[] EXCLUDE_PROPERTIES = { "password", "oldPassword", "newPassword", "confirmPassword" };;
    private static final ThreadLocal<Long> Time_THREADLOCAL = new NamedThreadLocal<>("cost time");

    @Before(value = "@annotation(controllerLog)")
    public void doBefore(JoinPoint joinPoint,Log controllerLog){
        Time_THREADLOCAL.set(System.currentTimeMillis());
    }


    @AfterReturning(pointcut = "@annotation(controllerLog)", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Log controllerLog, Object jsonResult) {
        recordLog(joinPoint, controllerLog, null, jsonResult);

    }
    @AfterThrowing(value = "@annotation(controllerLog)",throwing = "jsonException")
    public void doAfterThrowing(JoinPoint joinPoint,Log controllerLog,Exception jsonException){
        recordLog(joinPoint,controllerLog,jsonException,null);
    }

    private void recordLog(JoinPoint joinPoint, Log controllerLog, Exception e, Object jsonResult) {
       try {
           SysOperLog sysOperLog = new SysOperLog();
           LoginUser loginUser = SecurityUtils.getLoginUser();
           //处理log注解上的参数
           handleLogParam(joinPoint, controllerLog, sysOperLog,jsonResult);
           sysOperLog.setRequestMethod(ServletUtils.getRequest().getMethod());
           if(loginUser!=null){
               sysOperLog.setOperName(loginUser.getUsername());
           }
           sysOperLog.setOperUrl(ServletUtils.getRequest().getRequestURL().toString());
           sysOperLog.setOperIp(IpUtils.getIpAddr());
           sysOperLog.setOperLocation(AddressUtils.getRealAddressByIP(IpUtils.getIpAddr()));
           sysOperLog.setStatus(BusinessStatus.SUCCESS.ordinal());
           String className = joinPoint.getTarget().getClass().getName();
           String methodName = joinPoint.getSignature().getName();
           sysOperLog.setMethod(className+"."+methodName+"()");
           sysOperLog.setCostTime(System.currentTimeMillis()-Time_THREADLOCAL.get());
           if(e!=null){
               sysOperLog.setStatus(BusinessStatus.ERROR.ordinal());
               sysOperLog.setErrorMsg(e.getMessage());
           }
           AsyncManager.me().execute(AsyncFactory.recordOperLog(sysOperLog));
       }
       catch (Exception exception){
           log.error(exception.getMessage());
           e.printStackTrace();
       }
       finally {
           Time_THREADLOCAL.remove();
       }
    }

    /**
     * 处理log注解上的参数
     *
     * @param joinPoint
     * @param controllerLog
     * @param sysOperLog
     */
    private void handleLogParam(JoinPoint joinPoint, Log controllerLog, SysOperLog sysOperLog,Object jsonResult) {
        sysOperLog.setBusinessType(controllerLog.BusinessType().ordinal());
        sysOperLog.setTitle(controllerLog.title());
        sysOperLog.setOperatorType(controllerLog.operatorType().ordinal());
        if (controllerLog.isKeepRequestParam()) {
            setRequestParam(joinPoint, sysOperLog, controllerLog.filterParam());
        }
        // 是否需要保存response，参数和值
        if (controllerLog.isKeepResponseParam() && StringUtils.isNotNull(jsonResult))
        {
            sysOperLog.setJsonResult(StringUtils.substring(JSON.toJSONString(jsonResult), 0, 2000));
        }

    }

    /**
     * 设置请求参数
     *
     * @param
     * @param filterParam
     */
    private void setRequestParam(JoinPoint joinPoint, SysOperLog operLog, String[] filterParam) {
        //根据请求方式决定过滤请求参数
        Map<?,?> params = ServletUtils.getParamMap(ServletUtils.getRequest());
        String requestMethod = ServletUtils.getRequest().getMethod();
        if (StringUtils.isEmpty(params) &&
                (HttpMethod.PUT.name().equals(requestMethod) || HttpMethod.POST.name().equals(requestMethod))) {
            //得到过滤后的请求参数
            String requestParam = arraysToString(joinPoint.getArgs(), filterParam);
            operLog.setOperParam(StringUtils.substring(requestParam,0,2000));
        } else {
            operLog.setOperParam(StringUtils.substring(JSON.toJSONString(params, excludePropertyPreFilter(filterParam)), 0, 2000));
        }
    }

    /**
     * 拼装请求参数
     * @param args
     * @param filterParam
     * @return
     */
    private String arraysToString(Object[] args, String[] filterParam) {
        String params = "";
        if (filterParam.length > 0 && args != null) {
            for (Object o : args) {
                    if(!StringUtils.isNull(o)&&!isFilterObject(o)){
                       try {
                           String jsonString = JSON.toJSONString(o,excludePropertyPreFilter(filterParam));
                            params+=jsonString.toString()+" ";
                       }
                       catch (Exception e){

                       }
                    }
            }
        }
        return params.trim();
    }

    private PropertyPreExcludeFilter excludePropertyPreFilter(String[] filterParam) {
        return new PropertyPreExcludeFilter().addExcludes(ArrayUtils.addAll(EXCLUDE_PROPERTIES,filterParam));
    }

    /**
     * 是否过滤对应的对象
     * @param o
     * @return
     */
    private boolean isFilterObject(Object o) {
        Class<?> classz = o.getClass();
        if(classz.isArray()){
            return classz.componentType().isAssignableFrom(MultipartFile.class);
        } else if (classz.isAssignableFrom(Collection.class)) {
            Collection collection = (Collection) o;
            for(Object value : collection){
                return value instanceof MultipartFile;
            }
        } else if (Map.class.isAssignableFrom(classz)) {
            Map map = (Map) o;
            for(Object o1:map.entrySet()){
                Map.Entry entry = (Map.Entry) o1;
                return entry instanceof MultipartFile;
            }
        }
        return o instanceof MultipartFile||o instanceof HttpServletRequest||o instanceof HttpServletResponse
                || o instanceof BindingResult;

    }


}
