package com.tajkun.ad.search.mysql.dto;

import com.tajkun.ad.search.mysql.constant.OpType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.font.OpenType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @program: tajkun-ad
 * @description: 根据BinlogRowData构造的更简单的数据对象
 * @author: Jiakun
 * @create: 2020-04-28 20:38
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MySqlRowData {

    private String tableName;
    // 业务用到的层级关系
    private String level;
    private OpType opType;
    // afterData
    private List<Map<String, String>> fieldValueMap = new ArrayList<>();

}