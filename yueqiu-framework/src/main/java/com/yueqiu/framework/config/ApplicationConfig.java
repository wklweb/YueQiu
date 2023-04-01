package com.yueqiu.framework.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 自动扫描mapper 接口
 */
@Configuration
@EnableAspectJAutoProxy(exposeProxy = true)
@MapperScan("com.yueqiu.**.mapper")
public class ApplicationConfig {

}
