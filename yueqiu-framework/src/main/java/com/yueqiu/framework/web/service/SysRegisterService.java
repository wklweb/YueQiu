package com.yueqiu.framework.web.service;

import com.yueqiu.common.constant.Constants;
import com.yueqiu.common.constant.UserConstants;
import com.yueqiu.common.domain.entity.Register;
import com.yueqiu.common.domain.entity.SysUser;
import com.yueqiu.common.utils.MessageUtils;
import com.yueqiu.common.utils.SecurityUtils;
import com.yueqiu.common.utils.StringUtils;
import com.yueqiu.framework.manager.AsyncFactory;
import com.yueqiu.framework.manager.AsyncManager;
import com.yueqiu.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 注册服务业务
 */
@Service
public class SysRegisterService {
    @Autowired
    private SysUserService sysUserService;


    public String register(Register register) {
        String username = register.getUsername(), password = register.getPassword(),msg = "";
        SysUser sysUser = new SysUser();
        sysUser.setUserName(username);
        if (StringUtils.isNull(register)) {
            msg = "注册用户不能为空";
        } else if (username!=null&&username.length() > UserConstants.USERNAME_MAX_LENGTH ||
                username.length() < UserConstants.USERNAME_MIN_LENGTH) {
            msg = "用户名长度必须在2-10范围内";
        } else if (password!=null&&password.length() > UserConstants.PASSWORD_MAX_LENGTH ||
                password.length() < UserConstants.PASSWORD_MIN_LENGTH) {
            msg = "用户密码长度必须在2-10内";
        } else if (UserConstants.USERNAME_NOT_UNIQUE.equals(sysUserService.checkUserName(sysUser))) {
            msg = "用户名:["+username+"]已经存在";
        }
        else {
            sysUser.setPassword(SecurityUtils.encryptPassword(password));
            sysUser.setNickName(username);
            int result = sysUserService.addSysUser(sysUser);
            if(result!=1){
                msg = "注册失败,请联系系统管理人员";
            }
            else {
                AsyncManager.me().execute(AsyncFactory.recordLoginInfo(username, Constants.REGISTER, MessageUtils.getMessage("user.register.success",username)));
            }
        }
        return msg;
    }
}
