package com.yueqiu.framework.security.context;

import org.springframework.security.core.Authentication;

/**
 * 身份验证信息(保存登录用户的信息)
 *
 * @author ruoyi
 */
public class AuthenticationContextHolder {
    //存储线程本地数据,保证线程安全
    private static final ThreadLocal<Authentication> contextHolder = new ThreadLocal<>();

    public static Authentication getContext()
    {
        return contextHolder.get();
    }

    public static void setContext(Authentication context)
    {
        contextHolder.set(context);
    }

    public static void clearContext()
    {
        contextHolder.remove();
    }
}
