package com.tajkun.ad.search.runner;

import com.tajkun.ad.search.mysql.BinlogClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-04-29 10:28
 **/
@Slf4j
@Component
public class BinlogRunner implements CommandLineRunner {

    private final BinlogClient client;

    @Autowired
    public BinlogRunner(BinlogClient client) {
        this.client = client;
    }

    // 当springboot项目启动时运行
    @Override
    public void run(String... args) throws Exception {
        log.info("Coming in BinlogRunner...");
        client.connect();
    }

}