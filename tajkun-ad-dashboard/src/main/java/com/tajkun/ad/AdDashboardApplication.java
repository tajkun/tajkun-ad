package com.tajkun.ad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-05-03 16:31
 **/
@EnableEurekaClient
@SpringBootApplication
@EnableHystrixDashboard
public class AdDashboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdDashboardApplication.class, args);
    }
}