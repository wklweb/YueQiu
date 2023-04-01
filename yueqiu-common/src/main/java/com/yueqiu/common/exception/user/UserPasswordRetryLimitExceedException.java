package com.yueqiu.common.exception.user;

public class UserPasswordRetryLimitExceedException extends UserException{
    public UserPasswordRetryLimitExceedException(int retryCount, int lockTime) {
        super("user.password.retry.limit.exceed", new Object[]{retryCount,lockTime});
    }
}
