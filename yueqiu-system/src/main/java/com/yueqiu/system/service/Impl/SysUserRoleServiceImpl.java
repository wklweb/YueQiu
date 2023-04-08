package com.yueqiu.system.service.Impl;

import com.yueqiu.common.domain.entity.SysRole;
import com.yueqiu.common.domain.entity.SysUserRole;
import com.yueqiu.common.utils.StringUtils;
import com.yueqiu.system.mapper.SysRoleMapper;
import com.yueqiu.system.mapper.SysUserRoleMapper;
import com.yueqiu.system.service.SysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class SysUserRoleServiceImpl implements SysUserRoleService {

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;


    @Override
    public void insertUserRole(Long userId, Long[] roleIds,StringBuilder stringBuilder) {
        if (StringUtils.isNotEmpty(roleIds))
        {
            // 新增用户与角色管理
            List<SysUserRole> list = new ArrayList<SysUserRole>(roleIds.length);
            for (Long roleId : roleIds)
            {
                if(roleId!=null){
                   SysUserRole ur = new SysUserRole();
                   ur.setUserId(userId);
                   ur.setRoleId(roleId);
                   SysRole role = sysRoleMapper.selectRoleByRoleId(roleId);
                   stringBuilder.append(role==null?"":role.getRoleName()+",");
                   list.add(ur);
               }
            }
            if(list.size()>0){
                sysUserRoleMapper.batchUserRole(list);
            }

        }
    }

    @Override
    public List<SysUserRole> selectUserRole(Long userId) {
        return sysUserRoleMapper.selectUserRoleByUserId(userId);
    }

    @Override
    public Long[] getRoleIdsByUserId(Long userId) {
        List<SysUserRole> list = selectUserRole(userId);
        Long[] roleIds = new Long[list.size()];
        int i = 0;
        if(list.size()>0){
            for(SysUserRole sysUserRole : list){
                roleIds[i++] = sysUserRole.getRoleId();
            }
        }
        return roleIds;
    }
}
