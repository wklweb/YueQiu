package com.yueqiu.common.domain.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.yueqiu.common.domain.BaseEntity;

import java.util.Date;

public class SysChatRecords extends BaseEntity {
    public static final Long serialVersionUID = 1L;

    private Long activityId;
    private String sender;
    private String target;
    private String msg;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date sendingTime;

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getSendingTime() {
        return sendingTime;
    }

    public void setSendingTime(Date sendingTime) {
        this.sendingTime = sendingTime;
    }
}
