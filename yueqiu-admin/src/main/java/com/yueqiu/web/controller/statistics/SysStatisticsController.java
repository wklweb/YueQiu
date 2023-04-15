package com.yueqiu.web.controller.statistics;

import com.yueqiu.common.domain.AjaxResult;
import com.yueqiu.common.domain.entity.SysOrder;
import com.yueqiu.system.service.SysOrderService;
import com.yueqiu.web.controller.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 数据统计服务
 */
@RestController
@Api(value = "统计销售", tags = "统计销售服务")
@RequestMapping("/statistics/order")
public class SysStatisticsController extends BaseController {

    @Autowired
    private SysOrderService sysOrderService;

    @GetMapping("/number")
    @PreAuthorize("@permission.hasPerms('monitor:online:query')")
    @ApiOperation(value = "今日订单", notes = "今日订单")
    public AjaxResult numbers(){
        Calendar calendar = Calendar.getInstance();
        SysOrder sysOrder = new SysOrder();
        sysOrder.getParams().put("beginTime",DateFormatUtils.format(calendar.getTime(),"YYYY-MM-dd 00:00:00"));
        List<SysOrder> sysOrderList = sysOrderService.selectOrderList(sysOrder, getLoginUser().getUserId());
        AjaxResult ajaxResult = AjaxResult.success();
        ajaxResult.put("size",sysOrderList.size());
        ajaxResult.put("todayMoney",sysOrderList.stream().mapToDouble(SysOrder::getMoneys).sum());
        ajaxResult.put("orderAveragePrice",
                sysOrderList.stream().mapToDouble(SysOrder::getMoneys).sum()/sysOrderList.size());
        ajaxResult.put("maxOrderPrice",sysOrderList.stream().mapToDouble(SysOrder::getMoneys).max());

        return ajaxResult;
    }







}
