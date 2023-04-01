package com.yueqiu.common.constant;

public class UserConstants {

    public static final int USERNAME_MAX_LENGTH = 10;
    public static final int USERNAME_MIN_LENGTH = 2;
    public static final int PASSWORD_MAX_LENGTH = 10;

    public static final int PASSWORD_MIN_LENGTH = 2;
    public static final String PASSWORD_REGULAR_EXPRESSION = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$";
    public static final String PHONE_REGULAR_EXPRESSION =
            "/^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$/";
}
