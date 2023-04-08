package com.yueqiu.system.service.Impl;

import com.yueqiu.common.domain.entity.SysShop;
import com.yueqiu.system.mapper.SysShopMapper;
import com.yueqiu.system.service.SysShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysShopServiceImpl implements SysShopService {

    @Autowired
    private SysShopMapper sysShopMapper;

    /**
     * 根据条件查询出多个店铺
     * @param sysShop
     * @return
     */
    @Override
    public List<SysShop> selectShopList(SysShop sysShop) {
        return sysShopMapper.selectShopList(sysShop);
    }
}
