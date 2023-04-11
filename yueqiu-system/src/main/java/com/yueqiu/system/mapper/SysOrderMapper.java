package com.yueqiu.system.mapper;

import com.yueqiu.common.domain.entity.SysOrder;

import java.util.List;

public interface SysOrderMapper {
    List<SysOrder> selectOrderList(SysOrder sysOrder);

    SysOrder selectOrderByOrderId(Long orderId);
}
