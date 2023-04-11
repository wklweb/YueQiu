package com.yueqiu.system.domain;

public class SysCache {

    private String remark;
    private String cacheValue;
    private String cacheKey;
    private String cacheName;

    public String getCacheValue() {
        return cacheValue;
    }

    public void setCacheValue(String cacheValue) {
        this.cacheValue = cacheValue;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCacheKey() {
        return cacheKey;
    }

    public void setCacheKey(String cacheKey) {
        this.cacheKey = cacheKey;
    }

    public String getCacheName() {
        return cacheName;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }

    public String getLoginTokenKey() {
        return cacheKey;
    }

    public void setLoginTokenKey(String loginTokenKey) {
        this.cacheKey = loginTokenKey;
    }

    public SysCache(String cacheKey, String remark) {
        this.cacheKey = cacheKey;
        this.remark = remark;
    }

    public SysCache(String cacheValue, String cacheKey, String cacheName) {
        this.cacheValue = cacheValue;
        this.cacheKey = cacheKey;
        this.cacheName = cacheName;
    }

    public SysCache() {
    }
}
