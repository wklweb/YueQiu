package com.yueqiu.web.controller.system;

import com.yueqiu.common.domain.AjaxResult;
import com.yueqiu.common.domain.entity.SysUser;
import com.yueqiu.common.domain.entity.TableInfo;
import com.yueqiu.common.utils.page.PageUtils;
import com.yueqiu.system.service.SysUserService;
import com.yueqiu.web.controller.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/system/user")
@Api(value = "系统用户", tags = "用户接口")
public class SysUserController extends BaseController {
    @Autowired
    private SysUserService sysUserService;

    @GetMapping("list")
    @ApiOperation(value = "查询用户信息", notes = "查询用户信息的全部信息")
    public TableInfo users(@RequestBody SysUser sysUser){
        startPage();
        List<SysUser> list = sysUserService.selectUserByUser(sysUser);
        return getTableInfo(list);
    }

    private void startPage() {
        PageUtils.startPage();
    }

}
