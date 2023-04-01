package com.yueqiu.common.exception.user;

public class CaptchaExpireException extends UserException{
    public CaptchaExpireException() {
        super("user.captcha.expire", null);
    }
}
