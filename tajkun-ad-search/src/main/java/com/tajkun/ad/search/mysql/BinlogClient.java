package com.tajkun.ad.search.mysql;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.tajkun.ad.search.mysql.listener.AggregationListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-04-29 10:10
 **/
@Slf4j
@Component
public class BinlogClient {

    private BinaryLogClient binaryLogClient;

    private final BinlogConfig config;
    private final AggregationListener listener;

    @Autowired
    public BinlogClient(BinlogConfig config, AggregationListener listener) {
        this.config = config;
        this.listener = listener;
    }

    // 程序启动时就应该调用该方法连接，开始监听
    public void connect() {
        // 新起一个线程去不断监听，防止主线程阻塞
        new Thread(() -> {

            // 配置连接信息
            binaryLogClient = new BinaryLogClient(config.getHost(), config.getPort(),
                    config.getUsername(), config.getPassword());
            if (!StringUtils.isEmpty(config.getBinlogName()) &&
            !config.getPosition().equals(-1L)) {
                binaryLogClient.setBinlogFilename(config.getBinlogName());
                binaryLogClient.setBinlogPosition(config.getPosition());
            }

            // 注册监听器
            binaryLogClient.registerEventListener(listener);

            // 连接
            try {
                log.info("connecting to mysql start...");
                binaryLogClient.connect();
                log.info("connecting to mysql done...");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void close() {
        try {
            binaryLogClient.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}