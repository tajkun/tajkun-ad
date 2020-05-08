package com.tajkun.ad.search.mysql.listener;

import com.tajkun.ad.search.mysql.dto.BinlogRowData;

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