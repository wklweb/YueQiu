package com.yueqiu.framework.config;

import com.yueqiu.common.utils.ServletUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * 服务配置
 */
@Component
public class ServiceConfig {


    public String getUrl() {
        HttpServletRequest request = ServletUtils.getRequest();
        return getUrl(request);
    }

    private String getUrl(HttpServletRequest request) {
        StringBuffer url = request.getRequestURL();
        //得到接口项目名前缀
        String urlContext = request.getServletContext().getContextPath();
        return url.delete(url.length()-urlContext.length(),url.length()).append(urlContext).toString();
    }
}
