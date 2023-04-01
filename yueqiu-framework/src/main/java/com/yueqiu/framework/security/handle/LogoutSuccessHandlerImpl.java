package com.yueqiu.framework.security.handle;

import com.yueqiu.common.constant.Constants;
import com.yueqiu.common.core.domain.model.LoginUser;
import com.yueqiu.common.utils.StringUtils;
import com.yueqiu.framework.manager.AsyncFactory;
import com.yueqiu.framework.manager.AsyncManager;
import com.yueqiu.framework.web.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 退出处理类
 */
@Configuration
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {
    @Autowired
    private TokenService tokenService;

    /**
     * 实现“/logout" 退出登录
     * @param request
     * @param response
     * @param authentication
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        LoginUser loginUser = tokenService.getLoginUser(request);
        if(StringUtils.isNotNull(loginUser)){
            String token = loginUser.getToken();
            String loginName = loginUser.getUsername();
            //删除用户缓存
            tokenService.delLogininfo(token);
            //记录用户退出日志
            AsyncManager.me().execute(AsyncFactory.recordLoginInfo(loginName, Constants.LOGOUT,"退出成功"));
        }
    }
}
