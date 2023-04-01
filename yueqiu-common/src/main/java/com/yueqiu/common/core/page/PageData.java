package com.yueqiu.common.core.page;

import com.yueqiu.common.utils.StringUtils;

/**
 * 分页数据
 */
public class PageData {
    private Integer pageNum;
    private Integer pageSize;
    private String orderBy;

    private String isASC = "asc";
    private Boolean reasonable = true;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrderBy() {
        if(StringUtils.isEmpty(orderBy)){
            return "";
        }
        return StringUtils.underToScoreCase(orderBy) + " " + this.isASC;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getIsASC() {
        return isASC;
    }

    public void setIsASC(String isASC) {
        if(StringUtils.isNotNull(isASC)){
            if("ascending".equals(isASC)){
                this.isASC = "asc";
            } else if ("descending".equals(isASC)) {
                this.isASC = "desc";
            }
        }
        this.isASC = isASC;
    }

    public Boolean getReasonable() {
        if(StringUtils.isNull(this.reasonable)){
            return Boolean.TRUE;
        }
        return this.reasonable;
    }

    public void setReasonable(Boolean reasonable) {
        this.reasonable = reasonable;
    }
}
