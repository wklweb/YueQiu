package com.yueqiu.framework.aspectj;

import com.yueqiu.common.annotation.GiveRole;
import com.yueqiu.common.domain.entity.SysForm;
import com.yueqiu.common.domain.entity.SysRole;
import com.yueqiu.common.domain.entity.SysUser;
import com.yueqiu.common.domain.entity.SysUserRole;
import com.yueqiu.common.exception.ServiceException;
import com.yueqiu.common.utils.StringUtils;
import com.yueqiu.system.service.SysRoleService;
import com.yueqiu.system.service.SysUserRoleService;
import com.yueqiu.system.service.SysUserService;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Aspect//注解该类为切面拓展类
@Component
public class GiveRoleAspect {

    public static final Logger log = LoggerFactory.getLogger(GiveRole.class);
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleService sysRoleService;


    @AfterReturning(pointcut = "@annotation(giveRole)", returning = "jsonResult")
    public void doAfter(JoinPoint joinPoint, GiveRole giveRole, Object jsonResult) {
        try {
            //普通用户入驻申请后进行角色授予
            String[] roleKeys = StringUtils.split(giveRole.roleKey(), ",");
            StringBuilder stringBuilder = new StringBuilder(roleKeys.length + 10);
            //需要加的角色ids
            Long[] roleIds = new Long[roleKeys.length];
            if (roleKeys.length > 0) {
                for (int i = 0; i < roleKeys.length; i++) {
                    SysRole role = new SysRole();
                    role.setRoleKey(roleKeys[i]);
                    List<SysRole> roleList = sysRoleService.selectRoleList(role);
                    roleIds[i] = roleList.get(0).roleId;
                }

            }
            //参数值
            Object[] values = joinPoint.getArgs();
            for (Object o : values) {
                if (o instanceof SysForm) {
                    SysForm sysForm = (SysForm) o;
                    SysUser sysUser = sysUserService.selectUserByLoginName(sysForm.getApplicant());
                    if (!sysUser.isAdmin()) {
                        roleIds = excludeRole(sysUser.getUserId(), roleIds);
                        sysUserRoleService.insertUserRole(sysUser.getUserId(), roleIds,stringBuilder);
                        log.info("当前用户:'{}',被授予'{}'角色", sysUser.getUserName(),
                                    StringUtils.removeEndIgnoreCase(stringBuilder.toString(),","));

                    } else {
                        throw new ServiceException("超级用户不需要授予其他角色");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 忽略已经存在的角色
     *
     * @param roleIds
     * @return
     */
    private Long[] excludeRole(Long userId, Long[] roleIds) {
        Long[] newRoles = new Long[roleIds.length];
        int index = 0;
        if (!StringUtils.isEmpty(roleIds)) {
            Long[] havaRoles = sysUserRoleService.getRoleIdsByUserId(userId);
            if (havaRoles.length > 0) {
                for (int i = 0; i < roleIds.length; i++) {
                    boolean sign = false;
                    for (Long rId : havaRoles) {
                        if (rId == roleIds[i]) {
                            sign = true;
                            break;
                        }
                    }
                    if (!sign) {
                        newRoles[index++] = roleIds[i];
                    }
                }
            }
            else {
                return roleIds;
            }
        }
        return newRoles;
    }

}
