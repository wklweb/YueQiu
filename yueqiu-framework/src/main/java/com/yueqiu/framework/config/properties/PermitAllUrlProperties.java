package com.yueqiu.framework.config.properties;

import com.yueqiu.common.annotation.Anonymous;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;

/**
 * 所有url属性
 */
@Configuration
public class PermitAllUrlProperties implements InitializingBean, ApplicationContextAware {

    private List<String> urls = new ArrayList<>();
    private ApplicationContext applicationContext;


    @Override
    public void afterPropertiesSet() throws Exception {
        RequestMappingHandlerMapping requestMappingHandlerMapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo,HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods();
        map.keySet().forEach(requestMappingInfo -> {
            HandlerMethod handlerMethod = map.get(requestMappingInfo);
            Anonymous method = AnnotationUtils.findAnnotation(handlerMethod.getMethod(),Anonymous.class);
            Optional.ofNullable(method).ifPresent(anonymous1 -> Objects.requireNonNull(requestMappingInfo.getPatternsCondition().getPatterns())
                    .forEach(url->urls.add(url))
            );

            Anonymous controller = AnnotationUtils.findAnnotation(handlerMethod.getBeanType(),Anonymous.class);
            Optional.ofNullable(controller).ifPresent(anonymous->Objects.requireNonNull(requestMappingInfo.getPatternsCondition().getPatterns())
                    .forEach(url->urls.add(url))
            );

        });

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
