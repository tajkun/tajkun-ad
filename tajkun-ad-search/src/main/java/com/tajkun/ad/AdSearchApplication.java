package com.tajkun.ad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-04-24 15:42
 **/
//@EnableDiscoveryClient
@EnableHystrixDashboard
@EnableHystrix
@EnableCircuitBreaker
@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
public class AdSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdSearchApplication.class, args);
    }
}