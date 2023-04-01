package com.yueqiu.system.mapper;

import java.util.Set;

public interface SysRoleMapper {
    Set<String> selectRolesByUserId(Long userId);
}
