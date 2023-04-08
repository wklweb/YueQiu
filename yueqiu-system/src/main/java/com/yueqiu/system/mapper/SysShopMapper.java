package com.yueqiu.system.mapper;

import com.yueqiu.common.domain.entity.SysShop;

import java.util.List;

public interface SysShopMapper {
    List<SysShop> selectShopList(SysShop sysShop);
}
