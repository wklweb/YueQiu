package com.yueqiu.common.filter;

import com.yueqiu.common.utils.StringUtils;
import org.springframework.http.MediaType;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RepeatedFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
           ServletRequest servletRequest = null;
           if(request instanceof HttpServletRequest){
               if(StringUtils.startsWithIgnoreCase(request.getContentType(),MediaType.APPLICATION_JSON_VALUE)){
                   servletRequest = new RepeatRequestWrapper((HttpServletRequest) request, (HttpServletResponse) response);
               }
           }

           if(servletRequest!=null){
               chain.doFilter(servletRequest,response);
           }
           else {
               chain.doFilter(request,response);
           }

    }

}
