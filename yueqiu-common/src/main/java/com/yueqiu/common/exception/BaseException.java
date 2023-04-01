package com.yueqiu.common.exception;

import com.yueqiu.common.utils.MessageUtils;
import com.yueqiu.common.utils.StringUtils;

public class BaseException extends RuntimeException {
    private String code;
    private String defaultMsg;
    private Object[] params;
    private String module;

    public BaseException(String code, String defaultMsg, Object[] params, String module) {
        this.code = code;
        this.defaultMsg = defaultMsg;
        this.params = params;
        this.module = module;
    }

    @Override
    public String getMessage() {
        String message = null;
        if (!StringUtils.isEmpty(code))
        {
            message = MessageUtils.getMessage(code,params);
        }
        if (message == null)
        {
            message = defaultMsg;
        }
        return message;
    }
}
