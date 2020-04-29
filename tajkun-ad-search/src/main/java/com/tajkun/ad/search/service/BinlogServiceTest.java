package com.tajkun.ad.search.service;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;
import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;

import java.io.IOException;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-04-26 22:06
 **/
public class BinlogServiceTest {

//    UpdateRowsEventData{tableId=111, includedColumnsBeforeUpdate={0, 1, 2, 3, 4, 5}, includedColumns={0, 1, 2, 3, 4, 5}, rows=[
//    {before=[1, jk, , 0, Thu Jan 01 08:00:00 CST 1970, Thu Jan 01 08:00:00 CST 1970], after=[2, jk, , 0, Thu Jan 01 08:00:00 CST 1970, Thu Jan 01 08:00:00 CST 1970]}
//]}
//    Thu Jan 01 08:00:00 CST 1970

    public static void main(String[] args) throws IOException {

        BinaryLogClient client = new BinaryLogClient("127.0.0.1", 3306,
                "root", "root");
        client.registerEventListener(event -> {
            EventData data = event.getData();
            if (data instanceof UpdateRowsEventData) {
                System.out.println("update-------------");
                System.out.println(data.toString());
                System.out.println(event.getHeader().getEventType());
            } else if (data instanceof WriteRowsEventData){
                System.out.println("write-------------");
                System.out.println(data.toString());
                System.out.println(event.getHeader().getEventType());
            } else if (data instanceof DeleteRowsEventData){
                System.out.println("delete-------------");
                System.out.println(data.toString());
                System.out.println(event.getHeader().getEventType());
            }
        });

        client.connect();
    }
}