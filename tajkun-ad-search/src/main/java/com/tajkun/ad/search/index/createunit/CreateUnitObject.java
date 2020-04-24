package com.tajkun.ad.search.index.createunit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-04-24 21:42
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUnitObject {

    private Long creativeId;
    private Long unitId;

    // key: creativeId-unitId
}