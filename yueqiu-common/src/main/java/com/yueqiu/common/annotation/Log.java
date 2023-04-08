package com.yueqiu.common.annotation;

import com.yueqiu.common.enums.BusinessType;
import com.yueqiu.common.enums.OperatorType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 记录操作日志
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.PARAMETER})
public @interface Log {
    /**
     * 操作类别
     *
     * @return
     */
    public BusinessType BusinessType() default BusinessType.OTHER;

    /**
     * 操作人员类别
     *
     * @return
     */
    public OperatorType operatorType() default OperatorType.OTHER;

    /**
     * 日志标题
     *
     * @return
     */
    public String title() default "";

    /**
     * 是否保存请求参数
     *
     * @return
     */
    public boolean isKeepRequestParam() default true;

    /**
     * 是否保存响应参数
     *
     * @return
     */
    public boolean isKeepResponseParam() default true;

    /**
     * 过滤指定的请求参数
     *
     * @return
     */
    public String[] filterParam() default "";

}
