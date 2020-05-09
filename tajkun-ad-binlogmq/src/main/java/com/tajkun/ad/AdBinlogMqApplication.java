package com.tajkun.ad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-05-09 16:24
 **/
@EnableEurekaClient
@SpringBootApplication
public class AdBinlogMqApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdBinlogMqApplication.class, args);
    }
}