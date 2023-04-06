package com.yueqiu.system.service;

import com.yueqiu.common.domain.entity.SysRole;

import java.util.List;
import java.util.Set;

public interface SysRoleService {
    Set<String> selectRolesByUserId(Long userId);

    List<SysRole> selectRoleList(SysRole role);


    boolean checkRoleUnique(SysRole sysRole);

    boolean checkRoleKeyUnique(SysRole role);

    int addRole(SysRole role);
}
