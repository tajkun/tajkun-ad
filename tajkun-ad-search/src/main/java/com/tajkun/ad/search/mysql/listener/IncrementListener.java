package com.tajkun.ad.search.mysql.listener;

import com.github.shyiko.mysql.binlog.event.EventType;
import com.tajkun.ad.search.mysql.constant.Constant;
import com.tajkun.ad.search.mysql.constant.OpType;
import com.tajkun.ad.search.mysql.dto.BinlogRowData;
import com.tajkun.ad.search.mysql.dto.MySqlRowData;
import com.tajkun.ad.search.mysql.dto.TableTemplate;
import com.tajkun.ad.search.sender.ISender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: tajkun-ad
 * @description: 增量数据处理器
 * @author: Jiakun
 * @create: 2020-04-28 21:11
 **/
@Slf4j
@Component
public class IncrementListener implements IListener{

    private final AggregationListener aggregationListener;

    @Resource(name = "indexSender")
    private ISender sender;

    @Autowired
    public IncrementListener(AggregationListener aggregationListener) {
        this.aggregationListener = aggregationListener;
    }

    // 应该在IncrementListener实例化时就将表注册到处理器（listener）
    @Override
    @PostConstruct
    public void register() {
        log.info("IncrementListener register db and table info");
        // 把所有的表都注册上同一个监听处理器
        Constant.table2Db.forEach((k, v) -> aggregationListener.register(v, k, this));
    }

    // 将binlogrowdata转为mysqlrowdata并投递
    @Override
    public void onEvent(BinlogRowData eventData) {

        TableTemplate tableTemplate = eventData.getTableTemplate();
        EventType eventType = eventData.getEventType();
        // 包装成最后需要投递的数据
        MySqlRowData rowData = new MySqlRowData();
        rowData.setTableName(tableTemplate.getTableName());
        // todo:
        rowData.setLevel(tableTemplate.getLevel());
        OpType opType = OpType.toOpType(eventType);
        rowData.setOpType(opType);
        // 取出模板中该操作对应的字段列表
        List<String> fieldList = tableTemplate.getOpTypeFieldSetMap().get(opType);
        System.out.println("fieldList: "+fieldList);
        if (null == fieldList) {
            log.warn("{} not support for {}", opType, tableTemplate.getTableName());
            return;
        }
        // 发生变化的列以及列值
        System.out.println("eventData.getAfterData(): "+eventData.getAfterData());
        for (Map<String, String> afterMap : eventData.getAfterData()) {
            Map<String, String> _afterMap = new HashMap<>();
            for (Map.Entry<String, String> entry : afterMap.entrySet()) {
                String colName = entry.getKey();
                String colValue = entry.getValue();
                _afterMap.put(colName, colValue);
            }
            rowData.getFieldValueMap().add(_afterMap);
        }
        System.out.println(rowData);
        sender.sender(rowData);
    }

}