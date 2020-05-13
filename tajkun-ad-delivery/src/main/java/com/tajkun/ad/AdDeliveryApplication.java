package com.tajkun.ad;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-04-22 09:35
 **/
@EnableFeignClients  // 目前仅用于监控
@EnableCircuitBreaker  // 用于监控
@EnableEurekaClient
@SpringBootApplication
@MapperScan("com.tajkun.ad.delivery.mapper")
public class AdDeliveryApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdDeliveryApplication.class, args);
    }

}