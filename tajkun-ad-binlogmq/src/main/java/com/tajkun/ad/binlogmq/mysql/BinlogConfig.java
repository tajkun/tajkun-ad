package com.tajkun.ad.binlogmq.mysql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-04-29 10:06
 **/
@Component
// 配置文件信息转化到java对象
@ConfigurationProperties(prefix = "adconfig.mysql")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BinlogConfig {

    private String host;
    private Integer port;
    private String username;
    private String password;
    private String binlogName;
    private Long position;
}