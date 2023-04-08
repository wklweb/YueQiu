package com.yueqiu.mobile.controller.personal;

import com.yueqiu.common.domain.AjaxResult;
import com.yueqiu.common.domain.entity.SysForm;
import com.yueqiu.common.domain.entity.SysShop;
import com.yueqiu.common.utils.SecurityUtils;
import com.yueqiu.system.service.SysFormService;
import com.yueqiu.system.service.SysShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "前台：店铺服务", tags = "服务接口")
@RequestMapping("/personal/shop")
public class shopController {

    @Autowired
    private SysFormService sysFormService;

    @PostMapping("/apply/{applicationType}")
    @ApiOperation(value = "申请入驻", notes = "申请入驻")
    public AjaxResult apply(@RequestBody SysForm sysForm,@PathVariable String applicationType){
        String username = SecurityUtils.getLoginUser().getUsername();
        sysForm.setApplicant(username);
        sysForm.setCreateBy(username);
        int result = sysFormService.addShopApplyForm(sysForm,applicationType);
        if(result==1){
            return AjaxResult.success("申请成功,等待回复");
        }
        else {
            return AjaxResult.error("申请失败,您已经申请过一次");
        }
    }
}
