package com.tajkun.ad.search.mysql.dto;

import com.tajkun.ad.search.mysql.constant.OpType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-04-26 22:51
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableTemplate {

    private String tableName;
    private String level;
    private Map<OpType, List<String>> opTypeFieldSetMap = new HashMap<>();
    // 字段索引 -> 字段名
    private Map<Integer, String> posMap = new HashMap<>();
}