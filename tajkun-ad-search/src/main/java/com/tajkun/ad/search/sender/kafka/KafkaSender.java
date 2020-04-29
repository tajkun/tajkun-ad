package com.tajkun.ad.search.sender.kafka;

import com.alibaba.fastjson.JSON;
import com.tajkun.ad.search.mysql.dto.MySqlRowData;
import com.tajkun.ad.search.sender.ISender;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @program: tajkun-ad
 * @description: 将rowdata发送到kafka，其他服务可以去监听这个topic来获取数据处理各自的事情
 * @author: Jiakun
 * @create: 2020-04-29 15:58
 **/
@Component("kafkaSender")
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
        kafkaTemplate.send(topic, JSON.toJSONString(rowData));
    }

    //
    @KafkaListener(topics = {"ad-search-mysql-data"}, groupId = "tajkun-ad-search")
    public void processMysqlRowData(ConsumerRecord<?, ?> record) {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            // 将topic中的消息反序列化为MySqlRowData对象
            MySqlRowData rowData = JSON.parseObject(message.toString(), MySqlRowData.class);
            System.out.println("kafka processMysqlRowData: " + JSON.toJSONString(rowData));
        }
    }
}