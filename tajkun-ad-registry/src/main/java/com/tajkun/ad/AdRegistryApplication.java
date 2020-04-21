package com.tajkun.ad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @program: tajkun-ad
 * @description: 服务注册启动类
 * @author: Jiakun
 * @create: 2020-04-21 11:31
 **/
@EnableEurekaServer
@SpringBootApplication
public class AdRegistryApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdRegistryApplication.class, args);
    }
}