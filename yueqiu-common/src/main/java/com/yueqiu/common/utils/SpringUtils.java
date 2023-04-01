package com.yueqiu.common.utils;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

@Component
public class SpringUtils implements BeanFactoryPostProcessor {
    private static  ConfigurableListableBeanFactory beanFactory = null;
    public static <T> T getBean(String name) {
        return (T) beanFactory.getBean(name);
    }
    public static <T> T getBean(Class<T> clz){
        return (T) beanFactory.getBean(clz);
    }
    public static <T> T getAopProxy(T invoker)
    {
        return (T) AopContext.currentProxy();
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
