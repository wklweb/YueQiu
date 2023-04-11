package com.yueqiu.system.service.Impl;

import com.yueqiu.common.core.domain.model.LoginUser;
import com.yueqiu.common.domain.entity.SysShop;
import com.yueqiu.common.domain.entity.SysUser;
import com.yueqiu.system.mapper.SysAreaMapper;
import com.yueqiu.system.mapper.SysShopMapper;
import com.yueqiu.system.service.SysShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysShopServiceImpl implements SysShopService {

    @Autowired
    private SysShopMapper sysShopMapper;
    @Autowired
    private SysAreaMapper sysAreaMapper;

    /**
     * 根据条件查询出多个店铺
     * @param sysShop
     * @return
     */
    @Override
    public List<SysShop> selectShopList(SysShop sysShop) {
        return sysShopMapper.selectShopList(sysShop);
    }

    @Override
    public SysShop selectShopById(Long shopId) {
        SysShop shop = sysShopMapper.selectShopById(shopId);
        return shop;
    }

    @Override
    public int changeShop(SysShop sysShop, LoginUser loginUser, String master) {
        if(master.equals(loginUser.getUsername())||SysUser.isAdmin(loginUser.getUserId())){
            return sysShopMapper.updateShop(sysShop);
        }
        return 0;
    }
}
