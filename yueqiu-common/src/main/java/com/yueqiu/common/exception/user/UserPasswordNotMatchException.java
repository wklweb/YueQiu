package com.yueqiu.common.exception.user;

public class UserPasswordNotMatchException extends UserException{
    public UserPasswordNotMatchException() {
        super("user.password.not.match",null);
    }
}
