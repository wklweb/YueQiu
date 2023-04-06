package com.yueqiu.system.service.Impl;


import com.yueqiu.common.constant.UserConstants;
import com.yueqiu.common.domain.entity.SysUser;
import com.yueqiu.system.mapper.SysUserMapper;
import com.yueqiu.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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

    @Override
    public String checkUserName(SysUser user) {
        SysUser sysUser = sysUserMapper.selectUserByUserName(user.getUserName());
        if(!Objects.isNull(sysUser)){
            return UserConstants.USERNAME_NOT_UNIQUE;
        }
        return UserConstants.USERNAME_UNIQUE;
    }

    @Override
    public String checkPhone(SysUser user) {
        SysUser sysUser = sysUserMapper.selectUserByUserName(user.getPhonenumber());
        if(!Objects.isNull(sysUser)){
            return UserConstants.PHONE_NOT_UNIQUE;
        }
        return UserConstants.PHONE_UNIQUE;
    }

    @Override
    public String checkEmail(SysUser user) {
        SysUser sysUser = sysUserMapper.selectUserByUserName(user.getPhonenumber());
        if(!Objects.isNull(sysUser)){
            return UserConstants.EMAIL_NOT_UNIQUE;
        }
        return UserConstants.EMAIL_UNIQUE;
    }

    @Override
    public int addSysUser(SysUser user) {
        int result = sysUserMapper.insertUser(user);
        return result;
    }

    /**
     * 更新用户头像
     * @param username
     * @param imgUrl
     * @return
     */
    @Override
    public boolean updateUserAvatar(String username, String imgUrl) {
        return sysUserMapper.updateUserAvatar(username,imgUrl)>0;
    }
}
