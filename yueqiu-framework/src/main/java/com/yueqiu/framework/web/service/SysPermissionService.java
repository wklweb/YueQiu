package com.yueqiu.framework.web.service;

import com.yueqiu.common.domain.entity.SysRole;
import com.yueqiu.common.domain.entity.SysUser;
import com.yueqiu.common.utils.StringUtils;
import com.yueqiu.system.service.SysMenuService;
import com.yueqiu.system.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Component
public class SysPermissionService {

    @Autowired
    private SysMenuService menuService;
    @Autowired
    private SysRoleService sysRoleService;
    public Set<String> getPermissions(SysUser sysUser) {
        Set<String> set = new HashSet<>();
        if(sysUser.isAdmin()){
            set.add("*:*:*");
        }
        else {
            List<SysRole> roles = sysUser.getRoles();
            if(!roles.isEmpty()&&roles.size()>0){
                for(SysRole role : roles){
                    Set<String> perms = menuService.selectPermissionsByRoleId(role.roleId);
                    role.setPermissions(perms);
                    set.addAll(perms);
                }
            }
            else
            {
                set.addAll(menuService.selectMenuPermsByUserId(sysUser.getUserId()));
            }
        }
        return set;

    }

    public Set<String> getRoles(SysUser sysUser) {
        Set<String> set = new HashSet<>();
        if(sysUser.isAdmin()){
            set.add("admin");
        }
        else {
           set.addAll(sysRoleService.selectRolesByUserId(sysUser.getUserId()));
        }
        return set;
    }
}
