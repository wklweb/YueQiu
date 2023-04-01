package com.yueqiu.system.service;

import java.util.Set;

public interface SysMenuService {
    Set<String> selectPermissionsByRoleId(Long roleId);
}
