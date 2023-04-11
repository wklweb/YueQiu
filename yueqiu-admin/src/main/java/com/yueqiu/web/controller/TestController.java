package com.yueqiu.web.controller;

import com.yueqiu.common.annotation.Resubmit;
import com.yueqiu.common.domain.AjaxResult;
import com.yueqiu.common.domain.entity.SysOrder;
import com.yueqiu.common.domain.entity.SysUser;
import com.yueqiu.common.domain.model.LoginBody;
import com.yueqiu.system.mapper.SysOrderMapper;
import com.yueqiu.system.mapper.SysUserMapper;
import com.yueqiu.web.controller.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;

@Controller
@Api(value = "测试接口", tags = "用户测试接口")
public class TestController extends BaseController {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysOrderMapper sysOrderMapper;

    @PostMapping("/test")
//    @PreAuthorize("@permission.hasPerms('system:user:list')")
    @ApiOperation(value = "查询用户信息1", notes = "查询用户信息1的notes信息")
    @Resubmit
    @ResponseBody
    public void show(@RequestBody LoginBody loginBody){
        Time time = new Time(System.currentTimeMillis());
        System.out.println(time);
    }
    @PostMapping("/testRequest")
    @Resubmit
    @ResponseBody
    public void show2(@RequestBody LoginBody loginBody){
        SysUser sysUser = sysUserMapper.selectUserById(1L);
        System.out.println(sysUser);
    }
//    @GetMapping("/testOrder")
//    @ResponseBody
//    public AjaxResult testOrder(@RequestBody SysOrder sysOrder){
//        return AjaxResult.success(sysOrderMapper.selectOrderList(sysOrder));
//    }
}
