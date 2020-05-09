package com.tajkun.ad.binlogrel.dto;

import com.github.shyiko.mysql.binlog.event.EventType;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @program: tajkun-ad
 * @description: 将binlog数据（第三方工具传来的event）转化为此java对象
 * @author: Jiakun
 * @create: 2020-04-28 16:08
 **/
@Data
public class BinlogRowData {

    private TableTemplate tableTemplate;
    private EventType eventType;
    // map <列名，列值>
    private List<Map<String, String>> afterData;
    // 仅update有
    private List<Map<String, String>> beforeDate;

}