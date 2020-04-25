package com.tajkun.ad.common.export.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-04-25 09:21
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnitTable {

    private Long unitId;
    private Integer unitStatus;
    private Integer positionType;
    private Long planId;
}