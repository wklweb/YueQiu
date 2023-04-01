package com.yueqiu.framework.web.service;

import com.yueqiu.common.core.domain.model.LoginUser;
import com.yueqiu.common.utils.SecurityUtils;
import com.yueqiu.common.utils.StringUtils;
import com.yueqiu.framework.security.context.PermissionContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Set;

@Service("permission")
public class PermissionService {

    private static String ALL_PERMISSION="*:*:*";

    /**
     * 判断用户有无权限
     * @param permission
     * @return
     */
    public boolean hasPerms(String permission)
    {
        if(StringUtils.isEmpty(permission)){
            return true;
        }
        LoginUser loginUser = SecurityUtils.getLoginUser();
        Set<String> permissions = loginUser.getPermissions();
        if(StringUtils.isNotNull(loginUser)&&!CollectionUtils.isEmpty(permissions)){
            PermissionContextHolder.setContext(permission);
            return hasPermissions(permissions,permission);

        }
        return false;
    }

    private boolean hasPermissions(Set<String> permissions, String permission) {
       return permissions.contains(ALL_PERMISSION)||permissions.contains(permission);
    }

}
