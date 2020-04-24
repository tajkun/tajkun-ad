package com.tajkun.ad.search.client.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-04-24 16:07
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromotionPlanRequest {

    private Long userId;
    private List<Long> ids;
}