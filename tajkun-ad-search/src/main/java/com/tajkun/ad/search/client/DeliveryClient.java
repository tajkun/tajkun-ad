package com.tajkun.ad.search.client;


import com.tajkun.ad.common.vo.CommonResponse;
import com.tajkun.ad.search.client.vo.PromotionPlan;
import com.tajkun.ad.search.client.vo.PromotionPlanRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-04-24 16:27
 **/
@FeignClient(value = "tajkun-ad-delivery", fallback = DeliveryClientHystrix.class)
public interface DeliveryClient {

    @RequestMapping(value = "/delivery/get/promotionPlan", method = RequestMethod.POST)
    CommonResponse<List<PromotionPlan>> getPlan(@RequestBody PromotionPlanRequest request);
}