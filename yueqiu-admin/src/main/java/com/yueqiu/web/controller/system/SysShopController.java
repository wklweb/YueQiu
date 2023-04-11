package com.yueqiu.web.controller.system;

import com.yueqiu.common.annotation.GiveRole;
import com.yueqiu.common.annotation.Log;
import com.yueqiu.common.annotation.Resubmit;
import com.yueqiu.common.core.domain.model.LoginUser;
import com.yueqiu.common.core.text.Convert;
import com.yueqiu.common.domain.AjaxResult;
import com.yueqiu.common.domain.entity.SysForm;
import com.yueqiu.common.domain.entity.SysShop;
import com.yueqiu.common.domain.entity.SysUser;
import com.yueqiu.common.domain.entity.TableInfo;
import com.yueqiu.common.enums.BusinessType;
import com.yueqiu.common.utils.StringUtils;
import com.yueqiu.system.service.SysFormService;
import com.yueqiu.system.service.SysShopService;
import com.yueqiu.web.controller.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(value = "系统店铺", tags = "店铺接口")
@RequestMapping("/system/shop")
public class SysShopController extends BaseController {
    @Autowired
    private SysShopService sysShopService;

    @Autowired
    private SysFormService sysFormService;

    @GetMapping("/list")
    @PreAuthorize("@permission.hasPerms('manager:shopList:list')")
    @ApiOperation(value = "获取店铺列表", notes = "获取店铺列表")
    public TableInfo shops(@RequestBody SysShop sysShop){
       startPage();
       List<SysShop> list = sysShopService.selectShopList(sysShop);
       return getTableInfo(list);
    }
    @GetMapping("/myShop")
    @PreAuthorize("@permission.hasPerms('manage:myShop:list')")
    @ApiOperation(value = "获取本店铺信息", notes = "获取本店铺信息")
    public AjaxResult myShop(){
        SysShop shop = sysShopService.selectShopById(getLoginUser().getUserId());
        if(StringUtils.isNotNull(shop)){
            return AjaxResult.success(shop);
        }
        return AjaxResult.error("未获取到店铺信息");
    }


    @GetMapping("/apply/{applicationType}")
    @PreAuthorize("@permission.hasPerms('manager:storeProcess:query')")
    @ApiOperation(value = "入驻申请", notes = "入驻申请列表")
    public TableInfo showApply(@RequestBody SysForm sysForm,@PathVariable String applicationType){
        startPage();
        List<SysForm> list =  sysFormService.selectShopApplyList(sysForm,applicationType);
        return getTableInfo(list);
    }

    @PostMapping("/doExamine/{result}")
    @PreAuthorize("@permission.hasPerms('manager:shop:edit')")
    @Resubmit
    @GiveRole(roleKey = "shopkeeper")
    @Log(title = "审批入驻",BusinessType = BusinessType.CHANGE)
    @ApiOperation(value = "审批入驻申请", notes = "审批入驻申请")
    public AjaxResult handlerApply(@RequestBody SysForm sysForm,@PathVariable Object result){
        boolean sign = Convert.toBool(result);
        LoginUser loginUser = getLoginUser();
        String status = null;
        if(sign){
            status = "1";
        }
        else {
            status = "0";
        }
        sysForm.setStatus(status);
        sysForm.setUpdateBy(loginUser.getUsername());
       return toAjax(sysFormService.updateForm(sysForm));
    }

    @PostMapping("/edit")
    @PreAuthorize("@permission.hasPerms('manager:shop:edit')")
    @Log(title = "修改店铺",BusinessType = BusinessType.CHANGE)
    @ApiOperation(value = "修改店铺", notes = "修改店铺")
    public AjaxResult editShop(@RequestBody SysShop sysShop){
        if(StringUtils.isNotNull(sysShop)){
            LoginUser loginUser = getLoginUser();
            String master = sysShop.getCreateBy();
             return toAjax(sysShopService.changeShop(sysShop,loginUser,master));
        }
        else {
            return AjaxResult.error("目标店铺不存在");
        }
    }




}
