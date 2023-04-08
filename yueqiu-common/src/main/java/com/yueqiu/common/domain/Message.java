package com.yueqiu.common.domain;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * 消息实体对象
 */
public class Message {
    public String from;
    public String goTo;
    public String msg;
    public Long roomId;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    public Date sendTime;


    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getGoTo() {
        return goTo;
    }

    public void setGoTo(String goTo) {
        this.goTo = goTo;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }
}
