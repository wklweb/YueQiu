package com.yueqiu.common.exception.user;

public class UserNotExistsException extends UserException{
    public UserNotExistsException() {
        super("not.null", null);
    }
}
