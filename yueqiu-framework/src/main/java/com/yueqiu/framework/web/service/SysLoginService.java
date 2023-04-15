package com.yueqiu.framework.web.service;

import com.alibaba.fastjson2.util.UUIDUtils;
import com.yueqiu.common.constant.CacheConstants;
import com.yueqiu.common.constant.Constants;
import com.yueqiu.common.constant.UserConstants;
import com.yueqiu.common.core.domain.model.LoginUser;
import com.yueqiu.common.core.redis.RedisCache;
import com.yueqiu.common.domain.entity.SysUser;
import com.yueqiu.common.exception.ServiceException;
import com.yueqiu.common.exception.user.*;
import com.yueqiu.common.utils.IpUtils;
import com.yueqiu.common.utils.MessageUtils;
import com.yueqiu.common.utils.SecurityUtils;
import com.yueqiu.common.utils.StringUtils;
import com.yueqiu.common.utils.date.DateUtils;
import com.yueqiu.framework.manager.AsyncFactory;
import com.yueqiu.framework.manager.AsyncManager;
import com.yueqiu.framework.security.context.AuthenticationContextHolder;
import com.yueqiu.system.service.ISysConfigService;
import com.yueqiu.system.service.SysUserService;
import org.apache.poi.ss.usermodel.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.unit.DataUnit;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class SysLoginService {
    @Autowired
    private ISysConfigService iSysConfigService;
    @Autowired
    private RedisCache redisCache;
    @Resource
    private AuthenticationManager authenticationManager;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private TokenService tokenService;

    public String login(String username, String password, String code, String uuid)
    {
        // 验证码校验
        validateCaptcha(username, code, uuid);
        // 账号密码前置验证
        loginPreCheck(username,password);

        //开始验证
        Authentication authentication = null;
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,password);
            AuthenticationContextHolder.setContext(authenticationToken);
            authentication = authenticationManager.authenticate(authenticationToken);
        }
        catch (Exception e){
            if(e instanceof BadCredentialsException){
                //数据库密码明文存储报错
                AsyncManager.me().execute(AsyncFactory.recordLoginInfo(username, Constants.LOGIN_FAIL,
                        MessageUtils.getMessage("user.password.not.match")));
                throw new UserPasswordNotMatchException();
            }
            else {
                AsyncManager.me().execute(AsyncFactory.recordLoginInfo(username, Constants.LOGIN_FAIL, e.getMessage()));
                throw new ServiceException(e.getMessage());
            }
        }
        finally {
            AuthenticationContextHolder.clearContext();
        }
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        //记录登录日志 和 更新用户
        AsyncManager.me().execute(AsyncFactory.recordLoginInfo(username, Constants.LOGIN_SUCCESS, MessageUtils.getMessage("user.login.success")));
        recordLoginInfo(loginUser.getUserId());

        return tokenService.createToken(loginUser);
    }


    private void recordLoginInfo(Long userId) {
        if(StringUtils.isNotNull(userId)){
            SysUser sysUser = new SysUser();
            sysUser.setUserId(userId);
            sysUser.setLoginIp(IpUtils.getIpAddr());
            sysUser.setLoginDate(DateUtils.getNowDate());
            sysUserService.updateUserInfo(sysUser);
        }
    }


    private void validateCaptcha(String username, String code, String uuid) {
        //验证码验证开关
        boolean isCaptchaEnabled = iSysConfigService.getCaptchaEnabled();
        if(isCaptchaEnabled==false){
            return;
        }
        String captchaKey = CacheConstants.CAPTCHA_CODE_KEY + uuid;
        String captchaCode = redisCache.getCacheObject(captchaKey);
        redisCache.deleteObject(captchaKey);
        if(captchaCode==null){
            AsyncManager.me().execute(AsyncFactory.recordLoginInfo(username,Constants.LOGIN_FAIL, MessageUtils.getMessage("user.captcha.expire")));
            throw new CaptchaExpireException();
        }
        if(!captchaCode.equals(code)){
            AsyncManager.me().execute(AsyncFactory.recordLoginInfo(username,Constants.LOGIN_FAIL,MessageUtils.getMessage("user.captcha.error")));
            throw new CaptchaErrorException();
        }

    }

    /**
     * 登陆前的校验
     * @param username
     * @param password
     */
    private void loginPreCheck(String username, String password) {
        if(StringUtils.isEmpty(username)||StringUtils.isEmpty(password)){
            AsyncManager.me().execute(AsyncFactory.recordLoginInfo(username,Constants.LOGIN_FAIL,MessageUtils.getMessage("not.null")));
            throw new UserNotExistsException();
        }
        //判断账号密码长度
        if(username.length()> UserConstants.USERNAME_MAX_LENGTH||username.length()<UserConstants.USERNAME_MIN_LENGTH){
            AsyncManager.me().execute(AsyncFactory.recordLoginInfo(username,Constants.LOGIN_FAIL,MessageUtils.getMessage("user.password.not.match")));
            throw new UserPasswordNotMatchException();
        }
        if(password.length() > UserConstants.PASSWORD_MAX_LENGTH||password.length()<UserConstants.PASSWORD_MIN_LENGTH){
            AsyncManager.me().execute(AsyncFactory.recordLoginInfo(username,Constants.LOGIN_FAIL,MessageUtils.getMessage("user.password.not.match")));
            throw new UserPasswordNotMatchException();
        }

        //校验ip黑名单
        String blockIp = iSysConfigService.selectConfigByKey("sys.login.blackIPList");
        if(IpUtils.isMatchedIp(blockIp,IpUtils.getIpAddr())){
            AsyncManager.me().execute(AsyncFactory.recordLoginInfo(username,Constants.LOGIN_FAIL,MessageUtils.getMessage("login.block")));
            throw new UserBlockException();
        }


    }

}
