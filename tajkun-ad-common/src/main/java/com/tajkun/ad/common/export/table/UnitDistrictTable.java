package com.tajkun.ad.common.export.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-04-25 09:28
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnitDistrictTable {

    private Long unitId;
    private String province;
    private String city;
}