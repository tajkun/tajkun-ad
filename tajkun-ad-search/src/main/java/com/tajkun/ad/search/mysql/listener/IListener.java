package com.tajkun.ad.search.mysql.listener;

import com.tajkun.ad.search.mysql.dto.BinlogRowData;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-04-28 16:13
 **/
public interface IListener {

    void register();
    void onEvent(BinlogRowData eventData);

}