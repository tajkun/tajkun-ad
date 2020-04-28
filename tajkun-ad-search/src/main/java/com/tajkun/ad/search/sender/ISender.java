package com.tajkun.ad.search.sender;

import com.tajkun.ad.search.mysql.dto.MySqlRowData;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-04-28 21:09
 **/
public interface ISender {

    void sender(MySqlRowData rowData);
}