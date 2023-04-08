package com.yueqiu.system.service;

import com.yueqiu.common.domain.entity.SysMenu;
import com.yueqiu.common.domain.model.Mtree;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface SysMenuService {
    Set<String> selectPermissionsByRoleId(Long roleId);

    Set<String> selectMenuPermsByUserId(Long userId);

    List<SysMenu> selectMenuList(Long userId);

    List<Mtree> buildMtree(List<SysMenu> sysMenuList);

    List<Long> buildMenuTree(Long roleId);

    SysMenu selectMenuById(Long menuId);
}
