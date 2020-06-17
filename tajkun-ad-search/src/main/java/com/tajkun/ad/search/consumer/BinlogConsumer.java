package com.tajkun.ad.search.consumer;

import com.alibaba.fastjson.JSON;
import com.tajkun.ad.binlogrel.dto.MySqlRowData;
import com.tajkun.ad.search.sender.ISender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @program: tajkun-ad
 * @description: 消息接收方
 * @author: Jiakun
 * @create: 2020-05-09 17:29
 **/
@Slf4j
@Component
public class BinlogConsumer {

    private final ISender sender;

    @Autowired
    public BinlogConsumer(ISender sender) {
        this.sender = sender;
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "ad.binlog.queue", durable = "true"),
            exchange = @Exchange(
                    value = "tajkun.ad.binlog.exchange",
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.TOPIC),
            key = {"ad.binlog.mysqlRowData"}))
    public void processMysqlRowData(String rabbitmqMessage) {
        if (rabbitmqMessage != null) {
            MySqlRowData rowData = JSON.parseObject(rabbitmqMessage, MySqlRowData.class);
            log.info("rabbitmq process MysqlRowData: {}", JSON.toJSONString(rowData));
            sender.sender(rowData);
        }
    }

}