package com.yueqiu.common.domain.entity;

import com.yueqiu.common.annotation.Excel;
import com.yueqiu.common.domain.BaseEntity;

public class SysOrderArea extends BaseEntity {
    public static final Long serialVersionUID = 1L;

    private Long orderId;
    private Long areaId;
    @Excel(name = "售后类型", readConverterExp = "0=整单退款,1=部分退款")
    private String afterType;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public String getAfterType() {
        return afterType;
    }

    public void setAfterType(String afterType) {
        this.afterType = afterType;
    }
}
