package com.tajkun.ad.binlogmq.mysql;

import com.alibaba.fastjson.JSON;
import com.tajkun.ad.binlogrel.constant.OpType;
import com.tajkun.ad.binlogrel.dto.ParseTemplate;
import com.tajkun.ad.binlogrel.dto.TableTemplate;
import com.tajkun.ad.binlogrel.dto.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * @program: tajkun-ad
 * @description: 加载json模板文件解析为模板类，并根据sql实现索引到列名的映射
 * @author: Jiakun
 * @create: 2020-04-28 14:55
 **/
@Slf4j
@Component
public class TemplateHolder {

    private ParseTemplate template;

    private final JdbcTemplate jdbcTemplate;

    private String SQL_SCHEMA = "select table_schema, table_name, column_name, " +
            "ordinal_position from information_schema.columns where table_schema = ? and table_name = ?";

    @Autowired
    public TemplateHolder(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // TemplateHolder被容器加载时，解析立马执行
    @PostConstruct
    private void init() {
        loadJson("template.json");
    }

    // 对外提供 通过表名获得表信息
    public TableTemplate getTable(String tableName) {
        return template.getTableTemplateMap().get(tableName);
    }

    // 加载json模板文件
    private void loadJson(String path) {
        // 获取resources下面的文件
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(path);

        try {
            Template template = JSON.parseObject(inputStream, Charset.defaultCharset(), Template.class);
            this.template = ParseTemplate.parse(template);
            loadMeta();
        } catch (IOException e) {
            log.error(e.getMessage());
            // 不让其运行
            throw new RuntimeException("fail to parse json file");
        }
    }

    // 索引到列的映射
    private void loadMeta() {

        for (Map.Entry<String, TableTemplate> entry : template.getTableTemplateMap().entrySet()) {

            TableTemplate table = entry.getValue();
            List<String> updateFields = table.getOpTypeFieldSetMap().get(OpType.UPDATE);
//            System.out.println("tableName: "+table.getTableName()+" updateFields: "+updateFields);
            List<String> insertFields = table.getOpTypeFieldSetMap().get(OpType.ADD);
            List<String> deleteFields = table.getOpTypeFieldSetMap().get(OpType.DELETE);
//            System.out.println("tableName: "+table.getTableName()+" deleteFields: "+deleteFields);

            System.out.println("dbName: "+template.getDatabase()+" tableName: "+table.getTableName());
            jdbcTemplate.query(SQL_SCHEMA, new Object[]{template.getDatabase(), table.getTableName()},
                    (resultSet, i) -> {
                        int pos = resultSet.getInt("ORDINAL_POSITION");
                        String colName = resultSet.getString("COLUMN_NAME");
                        if ((null != updateFields && updateFields.contains(colName)) ||
                                (null != insertFields && insertFields.contains(colName)) ||
                                (null != deleteFields && deleteFields.contains(colName))) {
                            table.getPosMap().put(pos -1, colName);
                        }
                        return null;
                    });
        }
    }

}