package com.yueqiu.web.swagger;

import com.yueqiu.common.config.YueQiuConfig;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Value("${swagger.isSwaggerEnabled}")
    public  boolean isSwaggerEnabled;
    @Value("${yueqiu.version}")
    public static String version;
    @Autowired
    private YueQiuConfig config;

    @Bean
    public Docket CreateRestApi(){
        return new Docket(DocumentationType.OAS_30)
                .enable(isSwaggerEnabled)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 设置标题
                .title("约个球系统_接口文档")
                // 描述
                .description("用于约球系统管理下的文档识别，包括用户模块，登录模块，场地模块信息等")
                // 作者信息
                .contact(new Contact(config.getName(), null, "qqqq1393553001@163.com"))
                // 版本
                .version("版本号:" + config.getVersion())
                .build();


    }


}
