package com.yueqiu.common.exception.user;

public class CaptchaErrorException extends UserException{
    public CaptchaErrorException() {
        super("user.captcha.error", null);
    }
}
