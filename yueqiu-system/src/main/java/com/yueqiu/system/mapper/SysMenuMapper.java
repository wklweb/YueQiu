package com.yueqiu.system.mapper;

import java.util.Set;

public interface SysMenuMapper {
    Set<String> selectPermissionsByRoleId(Long roleId);
}
