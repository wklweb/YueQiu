package com.yueqiu.common.domain.model;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.ss.usermodel.DataFormat;
import org.springframework.beans.factory.annotation.Value;

import javax.xml.crypto.Data;
import java.util.Date;

/**
 * 邮件实体信息
 */
public class MailInfo {
    private String title;
    private String text;
    private String to;

    private String time;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTime() {
        String time = DateFormatUtils.format(new Date(),"YYYY/MM/dd HH:mm:ss");
        return time;
    }

    public MailInfo(String title, String to) {
        this.title = title;
        this.to = to;
    }

    public MailInfo() {
    }
}
