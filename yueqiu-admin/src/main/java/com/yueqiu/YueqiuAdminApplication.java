package com.yueqiu;

import com.yueqiu.framework.web.service.WebSocketServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import springfox.documentation.oas.annotations.EnableOpenApi;


@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@EnableOpenApi
public class YueqiuAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(YueqiuAdminApplication.class, args);
    }

}
