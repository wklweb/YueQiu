package com.yueqiu.common.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Resubmit {

    public String message() default "当前提交已重复，请勿重复提交";

    public int interval() default 5000;
}
