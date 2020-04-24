package com.tajkun.ad.search.index.district;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-04-24 21:17
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DistrictObject {

    private Long unitId;
    private String province;
    private String city;

    // province-city
}