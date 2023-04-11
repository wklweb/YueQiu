package com.yueqiu.system.service.Impl;

import com.yueqiu.common.core.domain.model.LoginUser;
import com.yueqiu.common.domain.entity.SysUserOnline;
import com.yueqiu.common.utils.StringUtils;
import com.yueqiu.system.service.SysUserOnlineService;
import org.springframework.stereotype.Service;

@Service
public class SysUserOnlineServiceImpl implements SysUserOnlineService {
    @Override
    public SysUserOnline selectOnlineByInfo(String username, String ipaddr, LoginUser loginUser) {
        if(StringUtils.contains(username,loginUser.getUsername())&&StringUtils.contains(ipaddr,loginUser.getIpaddr())){
            return toUserOnline(loginUser);
        }
        return null;
    }

    @Override
    public SysUserOnline selectOnlineByIpaddr(String ipaddr, LoginUser loginUser) {
        if(StringUtils.contains(ipaddr,loginUser.getIpaddr())){
            return toUserOnline(loginUser);
        }
        return null;
    }

    @Override
    public SysUserOnline selectOnlineByUserName(String username, LoginUser loginUser) {
        if(StringUtils.contains(username,loginUser.getUsername())){
            return toUserOnline(loginUser);
        }
        return null;
    }

    @Override
    public SysUserOnline selectOnlineList(LoginUser loginUser) {
        return toUserOnline(loginUser);
    }

    private SysUserOnline toUserOnline( LoginUser loginUser) {
        SysUserOnline sysUserOnline = new SysUserOnline();
        sysUserOnline.setTokenId(loginUser.getToken());
        sysUserOnline.setUserName(loginUser.getUsername());
        sysUserOnline.setIpaddr(loginUser.getIpaddr());
        sysUserOnline.setBrowser(loginUser.getBrowser());
        sysUserOnline.setOs(loginUser.getOs());
        sysUserOnline.setLoginLocation(loginUser.getLoginLocation());
        sysUserOnline.setLoginTime(loginUser.getLoginTime());
        return sysUserOnline;
    }
}
