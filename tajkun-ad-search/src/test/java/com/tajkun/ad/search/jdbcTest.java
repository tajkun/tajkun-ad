package com.tajkun.ad.search;

import com.tajkun.ad.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-05-08 19:14
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class jdbcTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private String SQL_SCHEMA = "select table_schema, table_name, column_name, " +
            "ordinal_position from information_schema.columns where table_schema = ? and table_name = ?";

    String db = "tajkun_ad";
    String tableName = "ad_plan";

    @Test
    public void testQuery() {

        jdbcTemplate.query(SQL_SCHEMA, new Object[]{db, tableName},
                (resultSet, i) -> {
                    System.out.println("------进来了");

                    System.out.println("------结束了");
                    return null;
                });
    }



}