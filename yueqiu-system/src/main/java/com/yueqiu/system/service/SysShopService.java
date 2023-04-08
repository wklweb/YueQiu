package com.yueqiu.system.service;

import com.yueqiu.common.domain.entity.SysShop;

import java.util.List;

public interface SysShopService {
    List<SysShop> selectShopList(SysShop sysShop);
}
