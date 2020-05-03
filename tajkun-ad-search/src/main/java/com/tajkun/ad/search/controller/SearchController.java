package com.tajkun.ad.search.controller;

import com.alibaba.fastjson.JSON;
import com.tajkun.ad.common.annotation.IgnoreResponseAdvice;
import com.tajkun.ad.common.vo.CommonResponse;
import com.tajkun.ad.search.client.DeliveryClient;
import com.tajkun.ad.search.client.vo.PromotionPlan;
import com.tajkun.ad.search.client.vo.PromotionPlanRequest;
import com.tajkun.ad.search.retrieve.ISearch;
import com.tajkun.ad.search.retrieve.vo.SearchRequest;
import com.tajkun.ad.search.retrieve.vo.SearchResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-04-24 16:14
 **/
@Slf4j
@RestController
public class SearchController {

    private final ISearch search;
    private final RestTemplate restTemplate;
    private final DeliveryClient deliveryClient;


    @Autowired
    public SearchController(RestTemplate restTemplate, DeliveryClient deliveryClient, ISearch search) {
        this.restTemplate = restTemplate;
        this.deliveryClient = deliveryClient;
        this.search = search;
    }

    @SuppressWarnings("all")
    @IgnoreResponseAdvice
    @PostMapping("/getPlanByRibbon")
    public CommonResponse<List<PromotionPlan>> getPlanByRibbon(@RequestBody PromotionPlanRequest request) {
        log.info("tajkun-ad-search: getPlanByRibbon -> {}", JSON.toJSONString(request));
        return restTemplate.postForEntity("http://tajkun-ad-delivery/delivery/get/promptionPlan",
                request,
                CommonResponse.class).getBody();
    }

    @IgnoreResponseAdvice
    @PostMapping("/getPlan")
    public CommonResponse<List<PromotionPlan>> getPlan(@RequestBody PromotionPlanRequest request) {
        log.info("tajkun-ad-search: getPlan -> {}", JSON.toJSONString(request));
        return deliveryClient.getPlan(request);
    }

    @PostMapping("/fetchAds")
    public SearchResponse fetchAds(@RequestBody SearchRequest request) {
        log.info("tajkun-ad-aearch: fetchAds -> {}", JSON.toJSONString(request));
        return search.fetchAds(request);
    }

}