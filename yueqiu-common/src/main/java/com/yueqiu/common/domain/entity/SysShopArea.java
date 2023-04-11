package com.yueqiu.common.domain.entity;

import com.yueqiu.common.domain.BaseEntity;

public class SysShopArea extends BaseEntity {
    public static final Long serialVersionUID = 1L;
    private Long shopId;
    private Long AreaId;

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getAreaId() {
        return AreaId;
    }

    public void setAreaId(Long areaId) {
        AreaId = areaId;
    }
}
