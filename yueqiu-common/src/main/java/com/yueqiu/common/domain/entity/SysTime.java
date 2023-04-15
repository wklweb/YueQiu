package com.yueqiu.common.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yueqiu.common.annotation.Excel;
import com.yueqiu.common.domain.BaseEntity;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SysTime {
    public static final Long serialVersionUID = 1L;

    private Long timeId;
    private Long areaId;
    @Excel(name = "使用开始时间")
    @JsonFormat(pattern = "HH:mm:ss")
    private Time beginTime;
    @Excel(name = "使用截止时间")
    @JsonFormat(pattern = "HH:mm:ss")
    private Time endTime;
    private Double money;
    private String status;
    @Excel(name = "使用时间(天)")
    @JsonFormat(pattern = "YYYY-MM-dd")
    private String days;


    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public Long getTimeId() {
        return timeId;
    }

    public void setTimeId(Long timeId) {
        this.timeId = timeId;
    }

    public Time getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Time beginTime) {
        this.beginTime = beginTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
