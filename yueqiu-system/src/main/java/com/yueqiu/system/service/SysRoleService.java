package com.yueqiu.system.service;

import java.util.Set;

public interface SysRoleService {
    Set<String> selectRolesByUserId(Long userId);
}
