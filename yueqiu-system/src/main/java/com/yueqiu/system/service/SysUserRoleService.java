package com.yueqiu.system.service;

import com.yueqiu.common.domain.entity.SysUserRole;

import java.util.List;

public interface SysUserRoleService {

    void insertUserRole(Long userId, Long[] roleIds,StringBuilder stringBuilder);

    List<SysUserRole> selectUserRole(Long userId);

    Long[] getRoleIdsByUserId(Long userId);
}
