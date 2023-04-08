package com.yueqiu.web.controller.system;

import com.yueqiu.common.domain.AjaxResult;
import com.yueqiu.common.domain.BaseEntity;
import com.yueqiu.common.domain.entity.SysMenu;
import com.yueqiu.common.domain.model.Mtree;
import com.yueqiu.system.service.SysMenuService;
import com.yueqiu.web.controller.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/system/menu")
@Api(value = "菜单服务", tags = "菜单服务")
public class SysMenuController extends BaseController {
    @Autowired
    private SysMenuService sysMenuService;


    @GetMapping("/MtreeList/{roleId}")
    public AjaxResult getMtreeList(@PathVariable Long roleId) {
            AjaxResult ajaxResult = AjaxResult.success();
            List<SysMenu> sysMenuList = sysMenuService.selectMenuList(getLoginUser().getUserId());
            ajaxResult.put("menus",sysMenuService.buildMtree(sysMenuList));
            ajaxResult.put("menusIds",sysMenuService.buildMenuTree(roleId));
            return ajaxResult;
    }

    @GetMapping("/{menuId}")
    @ApiOperation(value = "根据菜单id获取菜单信息", notes = "根据菜单id获取菜单信息")
    private AjaxResult getMenuInfo(@PathVariable Long menuId){
        return AjaxResult.success(sysMenuService.selectMenuById(menuId));
    }




}
