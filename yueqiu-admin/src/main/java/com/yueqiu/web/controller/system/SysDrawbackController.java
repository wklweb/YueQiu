package com.yueqiu.web.controller.system;

import com.yueqiu.common.domain.AjaxResult;
import com.yueqiu.common.domain.entity.SysOrder;
import com.yueqiu.common.domain.entity.TableInfo;
import com.yueqiu.system.service.SysOrderService;
import com.yueqiu.web.controller.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 售后服务
 */
@RestController
@Api(value = "售后服务接口", tags = "售后服务接口")
@RequestMapping("/order/drawback")
public class SysDrawbackController extends BaseController {
    @Autowired
    private SysOrderService sysOrderService;

    @GetMapping("/list")
    @PreAuthorize("@permission.hasPerms('order:drawback:list')")
    @ApiOperation(value = "售后订单显示", tags = "售后订单显示")
    public TableInfo drawbackList(@RequestBody SysOrder sysOrder){
        List<SysOrder> sysOrderList = sysOrderService.selectDrawBackList(sysOrder);
        return getTableInfo(sysOrderList);
    }


}
