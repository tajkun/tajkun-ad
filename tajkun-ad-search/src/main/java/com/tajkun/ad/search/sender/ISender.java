package com.tajkun.ad.search.sender;

import com.tajkun.ad.search.mysql.dto.MySqlRowData;

/**
 * @program: tajkun-ad
 * @description: 投递增量数据的接口
 * @author: Jiakun
 * @create: 2020-04-28 21:09
 **/
public interface ISender {

    void sender(MySqlRowData rowData);
}