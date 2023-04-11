package com.yueqiu.system.service.Impl;

import com.yueqiu.common.domain.entity.SysArea;
import com.yueqiu.common.domain.entity.SysShop;
import com.yueqiu.common.domain.entity.SysUser;
import com.yueqiu.system.mapper.SysAreaMapper;
import com.yueqiu.system.service.SysAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysAreaServiceImpl implements SysAreaService {

    @Autowired
    private SysAreaMapper sysAreaMapper;
    /**
     * 根据条件查询出对应的场地列表
     * @param sysArea
     * @param shopId
     * @return
     */
    @Override
    public List<SysArea> getAreaList(SysArea sysArea,Long shopId) {
        List<SysArea> sysAreas = null;
        if(SysUser.isAdmin(shopId)){
            sysAreas = sysAreaMapper.selectAreaList(sysArea,null);
            return sysAreas;
        }
        sysAreas = sysAreaMapper.selectAreaList(sysArea,shopId);
        return sysAreas;
    }

    @Override
    public int addNewArea(SysArea sysArea,Long shopId) {
        sysArea.setShopId(shopId);
        int result = sysAreaMapper.insertArea(sysArea);
        return result;
    }
}
