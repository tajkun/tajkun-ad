package com.tajkun.ad.binlogmq.sender.kafka;

import com.alibaba.fastjson.JSON;
import com.tajkun.ad.binlogmq.sender.ISender;
import com.tajkun.ad.binlogrel.dto.MySqlRowData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


/**
 * @program: tajkun-ad
 * @description: 将rowdata发送到kafka，其他服务可以去监听这个topic来获取数据处理各自的事情
 * @author: Jiakun
 * @create: 2020-04-29 15:58
 **/
@Slf4j
@Component
public class KafkaSender implements ISender {

    @Value("${adconfig.kafka.topic}")
    private String topic;

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public KafkaSender(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sender(MySqlRowData rowData) {
        log.info("binlog kafka service send MySqlRowData");
        kafkaTemplate.send(topic, JSON.toJSONString(rowData));
    }

}