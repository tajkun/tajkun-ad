package com.tajkun.ad.binlogmq.mysql.listener;

import com.tajkun.ad.binlogrel.dto.BinlogRowData;

/**
 * @program: tajkun-ad
 * @description: 表注册器，将数据表进行注册，监听处理
 * @author: Jiakun
 * @create: 2020-04-28 16:13
 **/
public interface IListener {

    void register();
    void onEvent(BinlogRowData eventData);

}