package com.yueqiu.system.service;


import com.yueqiu.common.domain.entity.SysUser;

import java.util.List;

public interface SysUserService {
    SysUser selectUserByLoginName(String loginName);

    void updateUserInfo(SysUser user);

    List<SysUser> selectUserByUser(SysUser sysUser);

    String checkUserName(SysUser user);

    String checkPhone(SysUser user);

    String checkEmail(SysUser user);

    int addSysUser(SysUser user);
}
