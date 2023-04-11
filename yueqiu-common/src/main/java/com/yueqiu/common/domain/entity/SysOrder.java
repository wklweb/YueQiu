package com.yueqiu.common.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yueqiu.common.annotation.Excel;
import com.yueqiu.common.domain.BaseEntity;

import java.util.Date;
import java.util.List;

public class SysOrder extends BaseEntity {
    public static final Long serialVersionUID = 1L;

    private Long orderId;

    private String phonenumber;
    private String orderType;
    private String status;
    private String payStatus;
    private Double money;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date payTime;
    private Long shopId;
    @Excel(name = "售后类型",readConverterExp = "0=整单退款,1=部分退款")
    private String afterType;
    @Excel(name = "售后状态",readConverterExp = "0=未申请,1=已申请")
    private String afterStatus;
    private SysShop shop;
    private List<SysArea> childArea;


    public List<SysArea> getChildArea() {
        return childArea;
    }

    public void setChildArea(List<SysArea> childArea) {
        this.childArea = childArea;
    }

    public String getAfterStatus() {
        return afterStatus;
    }

    public void setAfterStatus(String after_status) {
        this.afterStatus = after_status;
    }

    public SysShop getShop() {
        return shop;
    }
    public void setShop(SysShop shop) {
        this.shop = shop;
    }

    public String getAfterType() {
        return afterType;
    }

    public void setAfterType(String afterType) {
        this.afterType = afterType;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }
}
