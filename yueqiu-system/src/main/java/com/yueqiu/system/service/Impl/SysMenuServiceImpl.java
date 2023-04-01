package com.yueqiu.system.service.Impl;

import com.yueqiu.system.mapper.SysMenuMapper;
import com.yueqiu.system.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Override
    public Set<String> selectPermissionsByRoleId(Long roleId) {
        return sysMenuMapper.selectPermissionsByRoleId(roleId);
    }
}
