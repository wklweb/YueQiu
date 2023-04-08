package com.yueqiu.common.domain.entity;

import com.yueqiu.common.annotation.Excel;
import com.yueqiu.common.domain.BaseEntity;

import java.sql.Time;
import java.util.List;

public class SysShop extends BaseEntity {
    public static final Long serialVersionUID = 1L;

    @Excel(name = "店铺编号",cellType = Excel.ColumnType.NUMERIC, prompt = "店铺编号")
    private Long shopId;

    @Excel(name = "店铺名称")
    private String shopName;

    @Excel(name = "店铺地址")
    private String location;

    @Excel(name = "店铺状态",readConverterExp = "0=营业,1=休息")
    private String status;

    @Excel(name = "店铺标签")
    private String storeLabel;

    @Excel(name = "店铺评分")
    private Double storeRating;

    @Excel(name = "营业开始时间")
    private Time businessStartTime;

    @Excel(name = "营业截至时间")
    private Time businessEndTime;

    @Excel(name = "是否注销",readConverterExp = "0=未注销,1=注销")
    private String cancelFlag;

    private List<SysArea> areas;


    public String getCancelFlag() {
        return cancelFlag;
    }

    public void setCancelFlag(String cancelFlag) {
        this.cancelFlag = cancelFlag;
    }

    public List<SysArea> getAreas() {
        return areas;
    }

    public void setAreas(List<SysArea> areas) {
        this.areas = areas;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStoreLabel() {
        return storeLabel;
    }

    public void setStoreLabel(String storeLabel) {
        this.storeLabel = storeLabel;
    }

    public Double getStoreRating() {
        return storeRating;
    }

    public void setStoreRating(Double storeRating) {
        this.storeRating = storeRating;
    }

    public Time getBusinessStartTime() {
        return businessStartTime;
    }

    public void setBusinessStartTime(Time businessStartTime) {
        this.businessStartTime = businessStartTime;
    }

    public Time getBusinessEndTime() {
        return businessEndTime;
    }

    public void setBusinessEndTime(Time businessEndTime) {
        this.businessEndTime = businessEndTime;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
