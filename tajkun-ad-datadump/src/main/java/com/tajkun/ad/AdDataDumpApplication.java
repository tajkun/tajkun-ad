package com.tajkun.ad;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-06-17 10:45
 **/
@EnableEurekaClient
@SpringBootApplication
@MapperScan("com.tajkun.ad.delivery.mapper")
public class AdDataDumpApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdDataDumpApplication.class, args);
    }
}