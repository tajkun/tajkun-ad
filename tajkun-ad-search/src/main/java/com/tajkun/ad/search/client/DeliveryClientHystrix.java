package com.tajkun.ad.search.client;

import com.tajkun.ad.common.vo.CommonResponse;
import com.tajkun.ad.search.client.vo.PromotionPlan;
import com.tajkun.ad.search.client.vo.PromotionPlanRequest;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-04-24 16:35
 **/
@Component
public class DeliveryClientHystrix implements DeliveryClient{

    @Override
    public CommonResponse<List<PromotionPlan>> getPlan(PromotionPlanRequest request) {
        return new CommonResponse<>(-1, "tajkun-ad-delivery error");
    }
}