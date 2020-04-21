package com.tajkun.ad;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @program: tajkun-ad
 * @description: Zuul网关启动类
 * @author: Jiakun
 * @create: 2020-04-21 14:34
 **/
@EnableZuulProxy
@SpringCloudApplication
public class AdGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdGatewayApplication.class, args);
    }
}