package com.yueqiu.system.mapper;
import com.yueqiu.common.domain.entity.SysUser;

import java.util.List;

public interface SysUserMapper {
    SysUser selectUserById(Long userId);


    void updateUserInfo(SysUser user);

    SysUser selectUserByUserName(String loginName);

    List<SysUser> selectUserList(SysUser sysUser);
}
