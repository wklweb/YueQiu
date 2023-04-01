package com.yueqiu.framework.security.handle;

import com.alibaba.fastjson.JSON;
import com.yueqiu.common.constant.HttpStatus;
import com.yueqiu.common.domain.AjaxResult;
import com.yueqiu.common.utils.ServletUtils;
import com.yueqiu.common.utils.StringUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * 认证失败处理类
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint, Serializable {


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        int code = HttpStatus.UNAUTHORIZED;
        String msg = StringUtils.format("请求访问：{}，认证失败，无法访问系统资源",request.getRequestURI());
        ServletUtils.renderString(response, JSON.toJSONString(AjaxResult.error(code,msg)));
    }
}
