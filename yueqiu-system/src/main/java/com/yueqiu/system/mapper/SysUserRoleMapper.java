package com.yueqiu.system.mapper;

import com.yueqiu.common.domain.entity.SysUserRole;

import java.util.List;

public interface SysUserRoleMapper {
    void assignRoles();

    void batchUserRole(List<SysUserRole> list);

    List<SysUserRole> selectUserRoleByUserId(Long userId);
}
