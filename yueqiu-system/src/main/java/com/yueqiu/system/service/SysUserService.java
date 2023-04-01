package com.yueqiu.system.service;


import com.yueqiu.common.domain.entity.SysUser;

import java.util.List;

public interface SysUserService {
    SysUser selectUserByLoginName(String loginName);

    void updateUserInfo(SysUser user);

    List<SysUser> selectUserByUser(SysUser sysUser);
}
