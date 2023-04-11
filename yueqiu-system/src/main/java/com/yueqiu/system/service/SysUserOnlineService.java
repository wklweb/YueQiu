package com.yueqiu.system.service;

import com.yueqiu.common.core.domain.model.LoginUser;
import com.yueqiu.common.domain.entity.SysUserOnline;

public interface SysUserOnlineService {

    SysUserOnline selectOnlineByInfo(String username, String ipaddr, LoginUser loginUser);

    SysUserOnline selectOnlineByIpaddr(String ipaddr, LoginUser loginUser);

    SysUserOnline selectOnlineByUserName(String username, LoginUser loginUser);

    SysUserOnline selectOnlineList(LoginUser loginUser);
}
