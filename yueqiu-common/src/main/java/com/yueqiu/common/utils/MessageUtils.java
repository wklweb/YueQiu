package com.yueqiu.common.utils;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public class MessageUtils {
    public static String getMessage(String code, Object...params) {
        MessageSource messageSource = SpringUtils.getBean(MessageSource.class);
        String msg = messageSource.getMessage(code,params, LocaleContextHolder.getLocale());
        return msg;
    }
}
