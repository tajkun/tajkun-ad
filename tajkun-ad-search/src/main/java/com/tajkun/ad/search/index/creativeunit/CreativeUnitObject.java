package com.tajkun.ad.search.index.creativeunit;

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
public class CreativeUnitObject {

    private Long creativeId;
    private Long unitId;

    // key: creativeId-unitId
}