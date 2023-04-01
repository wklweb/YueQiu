package com.yueqiu.common.exception.user;

import com.yueqiu.common.exception.BaseException;

public class UserException extends BaseException {

    public UserException(String code,Object[] params) {
        super(code, null, params, "user");
    }
}
