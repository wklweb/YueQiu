package com.yueqiu.web.controller;

import com.yueqiu.common.domain.entity.SysUser;
import com.yueqiu.system.mapper.SysUserMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Api(value = "测试接口", tags = "用户测试接口")
public class TestController {
    @Autowired
    private SysUserMapper sysUserMapper;

    @RequestMapping("/test")
    @PreAuthorize("@permission.hasPerms('system:user:list')")
    @ApiOperation(value = "查询用户信息1", notes = "查询用户信息1的notes信息")
    @ResponseBody
    public void show(){
        SysUser sysUser = sysUserMapper.selectUserById(1L);
        System.out.println(sysUser);
    }

}
