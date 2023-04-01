package com.yueqiu.system.service.Impl;


import com.yueqiu.common.domain.entity.SysUser;
import com.yueqiu.system.mapper.SysUserMapper;
import com.yueqiu.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser selectUserByLoginName(String loginName) {
        return sysUserMapper.selectUserByUserName(loginName);
    }

    @Override
    public void updateUserInfo(SysUser user) {
        sysUserMapper.updateUserInfo(user);
    }

    @Override
    public List<SysUser> selectUserByUser(SysUser sysUser) {

        return sysUserMapper.selectUserList(sysUser);
    }
}
