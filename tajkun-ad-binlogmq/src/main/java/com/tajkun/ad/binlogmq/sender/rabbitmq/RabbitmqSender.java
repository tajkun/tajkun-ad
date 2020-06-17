package com.tajkun.ad.binlogmq.sender.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.tajkun.ad.binlogmq.sender.ISender;
import com.tajkun.ad.binlogrel.dto.MySqlRowData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @program: tajkun-ad
 * @description: 将rowdata发送到rabbitmq，其他服务可以去监听
 * @author: Jiakun
 * @create: 2020-06-16 17:07
 **/
@Slf4j
@Component("rabbitmqSender")
public class RabbitmqSender implements ISender {

    @Value("${adconfig.rabbitmq.topic}")
    private String topic;

    private final AmqpTemplate amqpTemplate;

    @Autowired
    public RabbitmqSender(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    @Override
    public void sender(MySqlRowData rowData) {
        try {
            log.info("binlog rabbitmq service send MySqlRowData");
            this.amqpTemplate.convertAndSend(topic, JSON.toJSONString(rowData));
        } catch (Exception e) {
            log.error("binlog rabbitmq service send MySqlRowData err: "+e.getMessage());
        }

    }
}