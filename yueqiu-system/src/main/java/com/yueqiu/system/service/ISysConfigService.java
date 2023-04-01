package com.yueqiu.system.service;

public interface ISysConfigService {

    boolean getCaptchaEnabled();
    public String selectConfigByKey(String configKey);


}
