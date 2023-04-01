package com.yueqiu.framework.web.service;
import com.yueqiu.common.constant.UserConstants;
import com.yueqiu.common.core.domain.model.LoginUser;
import com.yueqiu.common.domain.entity.SysUser;
import com.yueqiu.common.enums.UserStatus;
import com.yueqiu.common.exception.ServiceException;
import com.yueqiu.common.exception.user.UserNotExistsException;
import com.yueqiu.common.utils.StringUtils;
import com.yueqiu.system.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(UserDetailServiceImpl.class);
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysPasswordService sysPasswordService;
    @Autowired
    private SysPermissionService syspermissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserService.selectUserByLoginName(username);
        if(StringUtils.isNull(sysUser)){
            log.info("用户{}不存在",username);
            throw new ServiceException("用户:"+username+"不存在");
        }
        else if(UserStatus.DISABLE.getCode().equals(sysUser.getStatus())){
            log.info("用户:{},已被禁用",username);
            throw new ServiceException("用户:"+username+",已被禁用");
        }
        else if(sysUser.getDelFlag().equals(UserStatus.DELETED.getCode())){
            log.info("用户:"+username+"被删除了");
            throw new ServiceException("用户:"+username+",已被删除");
        }
        sysPasswordService.validate(sysUser);
        return createLoginUser(sysUser);
    }

    private UserDetails createLoginUser(SysUser sysUser) {
        return new LoginUser(sysUser.getUserId(),sysUser,syspermissionService.getPermissions(sysUser));
    }

}
