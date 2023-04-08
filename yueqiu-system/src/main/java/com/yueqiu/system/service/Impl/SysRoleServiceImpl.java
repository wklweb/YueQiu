package com.yueqiu.system.service.Impl;

import com.yueqiu.common.constant.UserConstants;
import com.yueqiu.common.domain.entity.SysRole;
import com.yueqiu.common.utils.StringUtils;
import com.yueqiu.system.mapper.SysRoleMapper;
import com.yueqiu.system.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Override
    public Set<String> selectRolesByUserId(Long userId) {
        List<SysRole> perms = sysRoleMapper.selectRolesByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (SysRole perm : perms)
        {
            if (StringUtils.isNotNull(perm))
            {
                permsSet.addAll(Arrays.asList(perm.getRoleKey().trim().split(",")));
            }
        }
        return permsSet;
    }

    @Override
    public List<SysRole> selectRoleList(SysRole role) {
        return sysRoleMapper.selectRoleList(role);
    }

    @Override
    public boolean checkRoleUnique(SysRole sysRole) {
        Long roleId = sysRole.getRoleId()==null?-1L:sysRole.getRoleId();
        SysRole newRole = sysRoleMapper.selectRoleByName(sysRole.getRoleName());
        if(!StringUtils.isNull(newRole)&&newRole.getRoleId()!=roleId){
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public boolean checkRoleKeyUnique(SysRole sysRole) {
        Long roleId = sysRole.getRoleId()==null?-1L:sysRole.getRoleId();
        SysRole newRole = sysRoleMapper.selectRoleByKey(sysRole.getRoleKey());
        if(!StringUtils.isNull(newRole)&&newRole.getRoleId()!=roleId){
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public int addRole(SysRole role) {
        int result = sysRoleMapper.insertRole(role);
        return result;
    }


}
