package com.yueqiu.common.domain.entity;

import java.io.Serializable;
import java.util.List;

public class TableInfo implements Serializable {

    public static final Long serialVersionUID = 1L;
    /**
     * 总条数
     */
    private Long total;
    private List<?> date;
    private int code;
    private String msg;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<?> getDate() {
        return date;
    }

    public void setDate(List<?> date) {
        this.date = date;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public TableInfo() {
    }

    public TableInfo(Long total, List<?> date, int code, String msg) {
        this.total = total;
        this.date = date;
        this.code = code;
        this.msg = msg;
    }

}
