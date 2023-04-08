package com.yueqiu.framework.interceptor;

import com.alibaba.fastjson.JSON;
import com.yueqiu.common.annotation.Resubmit;
import com.yueqiu.common.domain.AjaxResult;
import com.yueqiu.common.utils.HttpUtils;
import com.yueqiu.common.utils.ServletUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 重复提交拦截器(防止重复提交)
 */
@Component
public abstract class ResubmitInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            Resubmit resubmit = method.getAnnotation(Resubmit.class);
            if(resubmit!=null){
                if(isResubmit(request,resubmit)){
                    AjaxResult ajaxResult = AjaxResult.error(resubmit.message());
                    ServletUtils.renderString(response, JSON.toJSONString(ajaxResult));
                    return false;
                }
            }
            return true;
        }
        return true;
    }

    /**
     * 判断是否重复提交
     * @param request
     * @param resubmit
     * @return
     */
    public abstract boolean isResubmit(HttpServletRequest request, Resubmit resubmit);
}
