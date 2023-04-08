package com.yueqiu.common.domain.entity;

import com.yueqiu.common.annotation.Excel;
import com.yueqiu.common.domain.BaseEntity;

public class SysOperLog extends BaseEntity {
    public static final Long serialVersionUID = 1L;

    /**
     * 操作id
     */
    @Excel(name = "操作序号", cellType = Excel.ColumnType.NUMERIC, prompt = "操作编号")
    private Long operId;

    @Excel(name = "操作标题")
    private String title;

    @Excel(name = "业务类型", readConverterExp = "0=其他,1=新增,2=删除,3=修改,4=查询,5=导出,6=导入,7=退出,8=清空数据")
    private Integer businessType;

    @Excel(name = "请求方法")
    private String method;

    @Excel(name = "请求方式")
    private String requestMethod;

    @Excel(name = "操作人员类别", readConverterExp = "0=其他,2=后台人员,3=移动端人员")
    private Integer operatorType;

    @Excel(name = "操作人")
    private String operName;

    @Excel(name = "请求路径")
    private String operUrl;

    @Excel(name = "主机地址")
    private String operIp;

    @Excel(name = "操作地点")
    private String operLocation;

    @Excel(name = "请求参数")
    private String operParam;

    @Excel(name = "响应参数")
    private String jsonResult;

    @Excel(name = "操作状态",readConverterExp = "0=正常,1=异常")
    private Integer status;

    @Excel(name = "错误信息")
    private String errorMsg;

    @Excel(name = "操作时间")
    private String operTime;

    @Excel(name = "消耗时间", suffix = "毫秒")
    private Long costTime;

    public Long getOperId() {
        return operId;
    }

    public void setOperId(Long operId) {
        this.operId = operId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public Integer getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(Integer operatorType) {
        this.operatorType = operatorType;
    }

    public String getOperName() {
        return operName;
    }

    public void setOperName(String operName) {
        this.operName = operName;
    }

    public String getOperUrl() {
        return operUrl;
    }

    public void setOperUrl(String operUrl) {
        this.operUrl = operUrl;
    }

    public String getOperIp() {
        return operIp;
    }

    public void setOperIp(String operIp) {
        this.operIp = operIp;
    }

    public String getOperLocation() {
        return operLocation;
    }

    public void setOperLocation(String operLocation) {
        this.operLocation = operLocation;
    }

    public String getOperParam() {
        return operParam;
    }

    public void setOperParam(String operParam) {
        this.operParam = operParam;
    }

    public String getJsonResult() {
        return jsonResult;
    }

    public void setJsonResult(String jsonResult) {
        this.jsonResult = jsonResult;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getOperTime() {
        return operTime;
    }

    public void setOperTime(String operTime) {
        this.operTime = operTime;
    }

    public Long getCostTime() {
        return costTime;
    }

    public void setCostTime(Long costTime) {
        this.costTime = costTime;
    }
}
