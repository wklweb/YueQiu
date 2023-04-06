package com.yueqiu.system.mapper;
import com.yueqiu.common.domain.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserMapper {
    int updateUserAvatar(@Param("userName") String userName, @Param("avatar") String avatar);

    SysUser selectUserById(Long userId);


    void updateUserInfo(SysUser user);

    SysUser selectUserByUserName(String loginName);

    List<SysUser> selectUserList(SysUser sysUser);

    int insertUser(SysUser user);
}
