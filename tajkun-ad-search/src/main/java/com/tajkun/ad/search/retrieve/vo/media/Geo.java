package com.tajkun.ad.search.retrieve.vo.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: tajkun-ad
 * @description: 地理位置
 * @author: Jiakun
 * @create: 2020-05-01 22:16
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Geo {

    // 维度
    private Float latitude;
    // 经度
    private Float longitude;
    private String city;
    private String province;

}