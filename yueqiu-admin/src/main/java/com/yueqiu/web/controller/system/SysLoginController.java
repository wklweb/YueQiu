package com.yueqiu.web.controller.system;

import com.yueqiu.common.constant.CacheConstants;
import com.yueqiu.common.constant.Constants;
import com.yueqiu.common.constant.UserConstants;
import com.yueqiu.common.core.domain.model.LoginUser;
import com.yueqiu.common.core.redis.RedisCache;
import com.yueqiu.common.domain.AjaxResult;
import com.yueqiu.common.domain.model.LoginBody;
import com.yueqiu.framework.web.service.SysLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Api(value = "登录接口",tags = "用户登录接口")
public class SysLoginController {
    @Autowired
    private SysLoginService sysLoginService;
    @Autowired
    private RedisCache redisCache;

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


}
