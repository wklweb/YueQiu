package com.yueqiu.common.exception.user;

public class UserBlockException extends UserException{
    public UserBlockException() {
        super("login.block", null);
    }
}
