package com.yueqiu.system.mapper;

import com.yueqiu.common.domain.entity.SysRole;

import java.util.List;
import java.util.Set;

public interface SysRoleMapper {
    List<SysRole> selectRolesByUserId(Long userId);

    List<SysRole> selectRoleList(SysRole role);

    int insertRole(SysRole role);

    SysRole selectRoleByName(String roleName);


    SysRole selectRoleByKey(String roleKey);

    SysRole selectRoleByRoleId(Long roleId);
}
