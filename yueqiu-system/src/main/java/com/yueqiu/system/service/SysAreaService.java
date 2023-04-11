package com.yueqiu.system.service;

import com.yueqiu.common.domain.entity.SysArea;
import com.yueqiu.common.domain.entity.SysShop;

import java.util.List;

public interface SysAreaService {
    List<SysArea> getAreaList(SysArea sysArea,Long shopId);

    int addNewArea(SysArea sysArea,Long userId);
}
