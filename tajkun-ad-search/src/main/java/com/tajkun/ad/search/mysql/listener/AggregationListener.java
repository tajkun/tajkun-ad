package com.tajkun.ad.search.mysql.listener;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;
import com.tajkun.ad.search.mysql.TemplateHolder;
import com.tajkun.ad.search.mysql.dto.BinlogRowData;
import com.tajkun.ad.search.mysql.dto.TableTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @program: tajkun-ad
 * @description: 监听binlog 并解析第三方工具传来的监听事件中的rowData为自己定义的数据对象
 * @author: Jiakun
 * @create: 2020-04-28 16:17
 **/
@Slf4j
@Component
public class AggregationListener implements BinaryLogClient.EventListener {

    private String database;
    private String tableName;
    // map<tableName, IListener> 每张表都可以定义不同的监听方法去做相应处理
    private Map<String, IListener> listenerMap = new HashMap<>();

    private final TemplateHolder templateHolder;

    @Autowired
    public AggregationListener(TemplateHolder templateHolder) {
        this.templateHolder = templateHolder;
    }

    // 数据库名+表名=key
    private String genKey(String tableName, String database) {
        return database+":"+tableName;
    }

    public void register(String _database, String _tableName, IListener iListener) {
        log.info("register : {}-{}", _database, _tableName);
        this.listenerMap.put(genKey(_database, _tableName), iListener);
    }

    @Override
    public void onEvent(Event event) {
        EventType eventType = event.getHeader().getEventType();
        log.debug("event type: {}", eventType);
        if (eventType == EventType.TABLE_MAP) {
            TableMapEventData data = event.getData();
            this.tableName = data.getTable();
            this.database = data.getDatabase();
            return;
        }
        if (eventType != EventType.EXT_WRITE_ROWS && eventType != EventType.EXT_DELETE_ROWS
        && eventType != EventType.EXT_UPDATE_ROWS) {
            return;
        }

        // 数据库名和表名是否完成填充
        if (StringUtils.isEmpty(database) || StringUtils.isEmpty(tableName)) {
            log.error("no meta data event");
            return;
        }

        // 找出对应表有兴趣的监听器
        String key = genKey(this.database, this.tableName);
        IListener iListener = this.listenerMap.get(key);
        if (null == iListener) {
            log.debug("skip {}", key);
            return;
        }
        log.info("trigger event: {}", eventType.name());

        try {
            BinlogRowData rowData = buildBinlogRowData(event.getData());
            if (rowData == null) {
                return;
            }
            rowData.setEventType(eventType);
            // 将rowData传给感兴趣的监听器处理
            iListener.onEvent(rowData);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } finally {
            this.database = "";
            this.tableName = "";
        }


    }

    private BinlogRowData buildBinlogRowData(EventData eventData) {
        TableTemplate tableTemplate = templateHolder.getTable(tableName);
        if (null == tableTemplate) {
            log.warn("table {} not found", tableName);
            return null;
        }
        // rowData -> List<Map<String, String>>
        List<Map<String, String>> afterMapList = new ArrayList<>();
        for (Serializable[] afterValue : getAfterValues(eventData)) {
            // map<columnName, columnValue>
            Map<String, String> afterMap = new HashMap<>();
            int colLen = afterValue.length;
            for (int i = 0; i < colLen; ++i) {
                // 取出当前位置对应的列名
                String colName = tableTemplate.getPosMap().get(i);
                // 如果没有则说明不关心这个列
                if (null == colName) {
                    log.debug("ignore column position: {}", i);
                    continue;
                }
                String colValue = afterValue[i].toString();
                afterMap.put(colName, colValue);
            }
            afterMapList.add(afterMap);
        }
        BinlogRowData binlogRowData = new BinlogRowData();
        binlogRowData.setAfterData(afterMapList);
        binlogRowData.setTableTemplate(tableTemplate);
        return binlogRowData;
    }

    private List<Serializable[]> getAfterValues(EventData eventData) {
        if (eventData instanceof WriteRowsEventData) {
            return ((WriteRowsEventData) eventData).getRows();
        }
        if (eventData instanceof UpdateRowsEventData) {
            return ((UpdateRowsEventData) eventData).getRows()
                    .stream().map(Map.Entry::getValue).collect(Collectors.toList());
        }
        if (eventData instanceof DeleteRowsEventData) {
            return ((DeleteRowsEventData) eventData).getRows();
        }
        return Collections.emptyList();
    }

}