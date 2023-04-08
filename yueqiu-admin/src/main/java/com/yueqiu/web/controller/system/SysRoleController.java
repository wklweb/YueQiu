package com.yueqiu.web.controller.system;

import com.yueqiu.common.domain.AjaxResult;
import com.yueqiu.common.domain.entity.SysRole;
import com.yueqiu.common.domain.entity.TableInfo;
import com.yueqiu.system.service.SysRoleService;
import com.yueqiu.web.controller.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(value = "系统角色", tags = "角色接口")
@RequestMapping("/system/role")
public class SysRoleController extends BaseController {
    @Autowired
    private SysRoleService sysRoleService;

    @GetMapping("/list")
    @PreAuthorize("@permission.hasPerms('system:role:list')")
    @ApiOperation(value = "查询角色", notes = "查询角色")
    public TableInfo roles(@RequestBody SysRole role) {
        startPage();
        List<SysRole> roles = sysRoleService.selectRoleList(role);
        return getTableInfo(roles);
    }

    @PostMapping("/add")
    @ApiOperation(value = "新增角色", notes = "新增角色")
    @PreAuthorize("@permission.hasPerms('system:role:add')")
    public AjaxResult add(@RequestBody SysRole role) {
        if (!sysRoleService.checkRoleUnique(role)){
            return AjaxResult.error("该角色名已存在");
        }
        if(!sysRoleService.checkRoleKeyUnique(role)){
            return AjaxResult.error("角色的权限标识已经存在");
        }
        int result = sysRoleService.addRole(role);
        if(result==1){
            return AjaxResult.success("新增成功");
        }
        else {
            return AjaxResult.error("新增失败");
        }

    }



}
