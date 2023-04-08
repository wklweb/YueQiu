package com.yueqiu.web.controller.system;

import com.yueqiu.common.constant.CacheConstants;
import com.yueqiu.common.constant.Constants;
import com.yueqiu.common.constant.UserConstants;
import com.yueqiu.common.core.domain.model.LoginUser;
import com.yueqiu.common.core.redis.RedisCache;
import com.yueqiu.common.domain.AjaxResult;
import com.yueqiu.common.domain.entity.SysRole;
import com.yueqiu.common.domain.entity.SysUser;
import com.yueqiu.common.domain.model.LoginBody;
import com.yueqiu.common.utils.SecurityUtils;
import com.yueqiu.common.utils.SpringUtils;
import com.yueqiu.framework.web.service.SysLoginService;
import com.yueqiu.framework.web.service.SysPermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Controller
@Api(value = "登录接口",tags = "用户登录接口")
public class SysLoginController {
    @Autowired
    private SysLoginService sysLoginService;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private SysPermissionService sysPermissionService;

    @PostMapping("/login")
    @ResponseBody
    @ApiOperation(value = "登录系统", notes = "根据账号密码登入系统")
    public AjaxResult login(@RequestBody LoginBody loginBody){
        AjaxResult ajaxResult = AjaxResult.success();
        String token = sysLoginService.login(loginBody.getUsername(),loginBody.getPassword(),loginBody.getCode(),loginBody.getUuid());
        ajaxResult.put("msg","登录成功");
        ajaxResult.put(Constants.Token,token);
        return ajaxResult;
    }

    @GetMapping("/getInfo")
    @ResponseBody
    @ApiOperation(value = "获取用户信息", notes = "获取用户信息")
    public AjaxResult userInfo(){
        SysUser sysUser = SecurityUtils.getLoginUser().getUser();
        Set<String> permissions = sysPermissionService.getPermissions(sysUser);
        Set<String> roles = sysPermissionService.getRoles(sysUser);
        AjaxResult ajaxResult = AjaxResult.success();
        ajaxResult.put("user",sysUser);
        ajaxResult.put("roles",roles);
        ajaxResult.put("permissions",permissions);
        return ajaxResult;
    }


}
