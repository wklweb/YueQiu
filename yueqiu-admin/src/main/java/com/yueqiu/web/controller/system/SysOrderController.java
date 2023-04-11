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

@RestController
@Api(value = "订单服务接口", tags = "订单服务")
@RequestMapping("/system/order")
public class SysOrderController extends BaseController {
    @Autowired
    private SysOrderService sysOrderService;


    @GetMapping("/list")
    @PreAuthorize("@permission.hasPerms('order:orderList:list')")
    @ApiOperation(value = "获取订单列表", notes = "订单列表")
    public TableInfo orders(@RequestBody SysOrder sysOrder){
        startPage();
        List<SysOrder> sysOrderList = sysOrderService.selectOrderList(sysOrder, getLoginUser().getUserId());
        return getTableInfo(sysOrderList);
    }


    @PostMapping("/{orderId}")
    @PreAuthorize("@permission.hasPerms('order:orderList:query')")
    public AjaxResult getInfo(@PathVariable Long orderId){
        return AjaxResult.success(sysOrderService.selectOrderByOrderId(orderId));
    }



}
