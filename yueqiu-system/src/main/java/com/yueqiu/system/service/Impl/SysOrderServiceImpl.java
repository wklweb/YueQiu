package com.yueqiu.system.service.Impl;

import com.yueqiu.common.domain.entity.SysOrder;
import com.yueqiu.common.domain.entity.SysUser;
import com.yueqiu.system.mapper.SysOrderMapper;
import com.yueqiu.system.service.SysOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SysOrderServiceImpl implements SysOrderService {

    @Autowired
    private SysOrderMapper sysOrderMapper;
    @Override
    public List<SysOrder> selectOrderList(SysOrder sysOrder, Long userId) {
        if(SysUser.isAdmin(userId)){
            return sysOrderMapper.selectOrderList(sysOrder);
        }
        else {
            sysOrder.getParams().clear();
            sysOrder.getParams().put("shopId",userId);
            return sysOrderMapper.selectOrderList(sysOrder);
        }
    }

    @Override
    public SysOrder selectOrderByOrderId(Long orderId) {
        return sysOrderMapper.selectOrderByOrderId(orderId);
    }
}
