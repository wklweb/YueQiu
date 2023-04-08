package com.yueqiu.system.mapper;

import com.yueqiu.common.domain.entity.SysMenu;
import com.yueqiu.common.domain.entity.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface SysMenuMapper {
    List<String> selectPermissionsByRoleId(Long roleId);

    List<SysRole> selectRolesByUserId(Long userId);

    List<String> selectMenuPermsByUserId(Long userId);

    List<SysMenu> selectMenuList(SysMenu sysMenu);

    List<SysMenu> selectMenuListByUserId(SysMenu sysMenu);


    List<SysMenu> selectMenuListByRoleId(SysMenu sysMenu);

    List<Long> selectMenuListByRoleId(@Param("roleId") Long roleId,@Param("menuCheckStrictly") boolean menuCheckStrictly);

    SysMenu selectMenuById(Long menuId);
}
