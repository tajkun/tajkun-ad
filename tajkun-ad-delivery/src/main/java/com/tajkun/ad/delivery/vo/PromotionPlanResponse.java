package com.tajkun.ad.delivery.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-04-22 21:32
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromotionPlanResponse {

    private Long id;
    private String planName;
}