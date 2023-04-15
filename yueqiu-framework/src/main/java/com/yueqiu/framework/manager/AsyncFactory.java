package com.yueqiu.framework.manager;

import com.yueqiu.common.constant.Constants;
import com.yueqiu.common.core.domain.model.LoginUser;
import com.yueqiu.common.domain.entity.SysOperLog;
import com.yueqiu.common.utils.*;
import com.yueqiu.common.utils.log.LogUtils;
import com.yueqiu.system.domain.SysLogininfor;
import com.yueqiu.system.service.ISysLogininforService;
import com.yueqiu.system.service.SysOperLogService;
import eu.bitwalker.useragentutils.UserAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;
import java.util.TimerTask;

public class AsyncFactory {

    private static final Logger sys_user_logger = LoggerFactory.getLogger("sys-user");
    /**
     * 任务：记录用户登录（注销）的日志操作
     * @param loginName
     * @param status
     * @param msg
     * @param params
     * @return
     */
    public static TimerTask recordLoginInfo(final String loginName, final String status, final String msg, final Object...params) {
        UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        String browser = String.valueOf(userAgent.getBrowser());
        String os = String.valueOf(userAgent.getOperatingSystem());
        String ip = IpUtils.getIpAddr();

        return new TimerTask() {
            @Override
            public void run() {
                String address = AddressUtils.getRealAddressByIP(ip);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(LogUtils.getBlock(ip));
                stringBuilder.append(address);
                stringBuilder.append(LogUtils.getBlock(loginName));
                stringBuilder.append(LogUtils.getBlock(status));
                stringBuilder.append(LogUtils.getBlock(msg));
                sys_user_logger.info(stringBuilder.toString());

                SysLogininfor sysLogininfor = new SysLogininfor();
                sysLogininfor.setBrowser(browser);
                sysLogininfor.setOs(os);
                sysLogininfor.setIpaddr(ip);
                sysLogininfor.setLoginLocation(address);
                sysLogininfor.setUserName(loginName);
                sysLogininfor.setMsg(msg);
                if(StringUtils.equalsAny(status,Constants.LOGIN_SUCCESS, Constants.LOGOUT, Constants.REGISTER)){
                    sysLogininfor.setStatus(Constants.SUCCESS);
                }
                else if (StringUtils.equalsAny(status,Constants.LOGIN_FAIL)) {
                    sysLogininfor.setStatus(Constants.FAIL);
                }
                SpringUtils.getBean(ISysLogininforService.class).insertLogininfor(sysLogininfor);

            }
        };

    }
    public static TimerTask recordOperLog(SysOperLog sysOperLog) {
        return new TimerTask() {
            @Override
            public void run() {
                SpringUtils.getBean(SysOperLogService.class).recordOperLog(sysOperLog);
            }
        };
    }

}
