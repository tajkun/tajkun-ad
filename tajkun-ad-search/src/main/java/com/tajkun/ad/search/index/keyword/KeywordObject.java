package com.tajkun.ad.search.index.keyword;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-04-24 18:02
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KeywordObject {

    private Long unitId;
    private String keyword;
}