package com.yueqiu.common.utils;

import com.yueqiu.common.core.text.Convert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ServletUtils {
    public static HttpServletRequest getRequest()
    {
        return getRequestAttributes().getRequest();
    }
    public static ServletRequestAttributes getRequestAttributes()
    {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        return (ServletRequestAttributes) attributes;
    }


    public static void renderString(HttpServletResponse response, String string)
    {
        try
        {
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static Boolean getParameterToBool(String reasonable) {
        return Convert.toBool(getRequest().getParameter(reasonable));
    }

    public static HttpServletResponse getResponse() {
        return getRequestAttributes().getResponse();
    }

    public static Map<String, String[]> getParamMap(HttpServletRequest request) {
        Map<String,String[]> map = new HashMap<>();
        map =  request.getParameterMap();
        return map;
    }
}
