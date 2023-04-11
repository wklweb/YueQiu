package com.yueqiu.system.service;

import com.yueqiu.common.domain.entity.SysOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysOrderService {
    List<SysOrder> selectOrderList(SysOrder sysOrder, Long userId);

    SysOrder selectOrderByOrderId(@Param(value = "orderId") Long orderId);

    List<SysOrder> selectDrawBackList(SysOrder sysOrder);
}
