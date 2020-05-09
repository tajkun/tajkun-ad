package com.tajkun.ad.binlogrel.dto;

import com.tajkun.ad.binlogrel.constant.OpType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: tajkun-ad
 * @description: 表的操作的相关信息，表字段索引到字段名的映射
 * @author: Jiakun
 * @create: 2020-04-26 22:51
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableTemplate {

    private String tableName;
    // 自定义表的逻辑级别
    private String level;
    // map<操作类型，操作类型所对应的列>
    private Map<OpType, List<String>> opTypeFieldSetMap = new HashMap<>();
    // map<字段索引, 字段名>
    private Map<Integer, String> posMap = new HashMap<>();
}