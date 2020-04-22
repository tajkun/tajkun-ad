package com.tajkun.ad.delivery.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-04-22 21:34
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromotionPlanGetRequest {

    private Long userId;
    private List<Long> ids;

    public boolean validate() {
        return userId != null && !CollectionUtils.isEmpty(ids);
    }
}