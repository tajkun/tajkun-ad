package com.tajkun.ad.search.mysql.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @program: tajkun-ad
 * @description: json模板文件的java对象表示
 * @author: Jiakun
 * @create: 2020-04-26 22:50
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Template {

    private String database;
    private List<JsonTable> tableList;
}