package com.tajkun.ad.search.consumer;

import com.alibaba.fastjson.JSON;
import com.tajkun.ad.binlogrel.dto.MySqlRowData;
import com.tajkun.ad.search.sender.ISender;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @program: tajkun-ad
 * @description:
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

    // 监听方法
    @KafkaListener(topics = {"tajkun-ad-binlog-mysql-rowData"}, groupId = "tajkun-ad-search")
    public void processMysqlRowData(ConsumerRecord<?, ?> record) {

        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            MySqlRowData rowData = JSON.parseObject(message.toString(), MySqlRowData.class);
            log.info("kafka process MysqlRowData: {}", JSON.toJSONString(rowData));
            sender.sender(rowData);
        }

    }

}