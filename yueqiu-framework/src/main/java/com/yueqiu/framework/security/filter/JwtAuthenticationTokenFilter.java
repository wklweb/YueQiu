package com.yueqiu.framework.security.filter;

import com.yueqiu.common.core.domain.model.LoginUser;
import com.yueqiu.common.utils.SecurityUtils;
import com.yueqiu.common.utils.StringUtils;
import com.yueqiu.framework.web.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * token认证拦截器
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private TokenService tokenService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        LoginUser loginUser = tokenService.getLoginUser(request);
        if(StringUtils.isNotNull(loginUser)&&StringUtils.isNull(SecurityUtils.getAuthentication())){
            tokenService.verifyToken(loginUser);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        }
//      在chain.doFilter()后面的代码，一般是对response执行的操作;
//
//      chain.doFiter()执行下一个过滤器或者业务处理器。
//
//      如果在doFilter()方法中，不写chain.doFilter()，业务无法继续往下处理；
        filterChain.doFilter(request,response);
    }
}
