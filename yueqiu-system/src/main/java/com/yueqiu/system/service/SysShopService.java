package com.yueqiu.system.service;

import com.yueqiu.common.core.domain.model.LoginUser;
import com.yueqiu.common.domain.entity.SysShop;

import java.util.List;

public interface SysShopService {
    List<SysShop> selectShopList(SysShop sysShop);

    SysShop selectShopById(Long shopId);

    int changeShop(SysShop sysShop, LoginUser loginUser, String master);
}
