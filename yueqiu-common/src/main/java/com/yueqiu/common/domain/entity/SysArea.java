package com.yueqiu.common.domain.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yueqiu.common.annotation.Excel;
import com.yueqiu.common.domain.BaseEntity;

import java.util.Date;
import java.util.List;

public class SysArea extends BaseEntity {
    public static final Long serialVersionUID = 1L;

    @Excel(name = "场地id", cellType = Excel.ColumnType.NUMERIC, prompt = "场地编号")
    private Long areaId;

    @Excel(name = "场地名称")
    private String name;

    @Excel(name = "店铺id")
    private Long shopId;

    @Excel(name = "场地类型", readConverterExp = "0=足球场,1=篮球场,2=乒乓球,3=羽毛球")
    private String areaType;

    private String isShow;

    @Excel(name = "场地图片",cellType = Excel.ColumnType.IMAGE)
    private String image;

    @Excel(name = "场地描述")
    private String description;

    @Excel(name = "场地位置")
    private String areaLocation;

    private String delFlag;

    @Excel(name = "场地库存")
    private String stock;

    private List<SysTime> times;

    public List<SysTime> getTimes() {
        return times;
    }

    public void setTimes(List<SysTime> times) {
        this.times = times;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAreaType() {
        return areaType;
    }

    public void setAreaType(String areaType) {
        this.areaType = areaType;
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAreaLocation() {
        return areaLocation;
    }

    public void setAreaLocation(String areaLocation) {
        this.areaLocation = areaLocation;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "SysArea{" +
                "areaId=" + areaId +
                ", name='" + name + '\'' +
                ", areaType='" + areaType + '\'' +
                ", isShow='" + isShow + '\'' +
                ", image='" + image + '\'' +
                ", description='" + description + '\'' +
                ", areaLocation='" + areaLocation + '\'' +
                ", delFlag='" + delFlag + '\'' +
                ", stock='" + stock + '\'' +
                '}';
    }
}
