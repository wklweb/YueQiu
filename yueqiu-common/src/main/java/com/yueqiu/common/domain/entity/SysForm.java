package com.yueqiu.common.domain.entity;

import com.yueqiu.common.annotation.Excel;
import com.yueqiu.common.domain.BaseEntity;

public class SysForm extends BaseEntity {
    public static final Long serialVersionUID = 1L;

    @Excel(name = "申请表id", cellType = Excel.ColumnType.NUMERIC)
    private Long formId;
    @Excel(name = "申请人")
    private String applicant;
    @Excel(name = "申请类型")
    private String applicationType;
    @Excel(name = "拒绝原因")
    private String reason;
    @Excel(name = "申请状态")
    private String status;

    private String delFlag;
    @Excel(name = "电话号码")
    private String phonenumber;
    @Excel(name = "店铺名称")
    private String shopName;
    @Excel(name = "审批人")
    private String reviewer;
    @Excel(name = "申请描述")
    private String description;

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public Long getFormId() {
        return formId;
    }

    public void setFormId(Long formId) {
        this.formId = formId;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
