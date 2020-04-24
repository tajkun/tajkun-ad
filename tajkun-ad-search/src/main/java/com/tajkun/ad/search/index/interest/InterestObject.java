package com.tajkun.ad.search.index.interest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-04-24 20:44
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InterestObject {

    private Long unitId;
    private String interestTag;

}