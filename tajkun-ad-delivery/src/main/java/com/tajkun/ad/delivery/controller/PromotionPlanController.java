package com.tajkun.ad.delivery.controller;

import com.alibaba.fastjson.JSON;
import com.tajkun.ad.common.exception.AdException;
import com.tajkun.ad.delivery.pojo.PromotionPlan;
import com.tajkun.ad.delivery.service.IPromotionPlanService;
import com.tajkun.ad.delivery.vo.PromotionPlanGetRequest;
import com.tajkun.ad.delivery.vo.PromotionPlanRequest;
import com.tajkun.ad.delivery.vo.PromotionPlanResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-04-23 15:16
 **/
@Slf4j
@RestController
public class PromotionPlanController {

    private final IPromotionPlanService promotionPlanService;

    @Autowired
    public PromotionPlanController(IPromotionPlanService promotionPlanService) {
        this.promotionPlanService = promotionPlanService;
    }

    @PostMapping("/create/promotionPlan")
    public PromotionPlanResponse createPlan(@RequestBody PromotionPlanRequest request) throws AdException {
        log.info("tajkun-ad-delivery: createPlan -> {}", JSON.toJSONString(request));
        return promotionPlanService.createPromotionPlan(request);
    }

    @PostMapping("/get/promotionPlan") // 这里使用post是因为反序列化request对象比较方便
    public List<PromotionPlan> getPlanByIds(@RequestBody PromotionPlanGetRequest request) throws AdException {
        log.info("tajkun-ad-delivery: getPlanByIds -> {}", JSON.toJSONString(request));
        return promotionPlanService.getPromotionPlanByIds(request);
    }

    @PutMapping("/update/promotionPlan") //
    public PromotionPlanResponse updatePlan(@RequestBody PromotionPlanRequest request) throws AdException {
        log.info("tajkun-ad-delivery: updatePlan -> {}", JSON.toJSONString(request));
        return promotionPlanService.updatePromotionPlan(request);
    }

    @PostMapping("/delete/promotionPlan")
    public void deletePlan(@RequestBody PromotionPlanRequest request) throws AdException {
        log.info("tajkun-ad-delivery: deletePlan -> {}", JSON.toJSONString(request));
        promotionPlanService.deleteAdPlan(request);
    }

    // 测试
    @PostMapping("/delete/promotionPlan1")
    public void deletePlan1(@RequestBody PromotionPlanRequest request) throws AdException {
        log.info("tajkun-ad-delivery: deletePlan -> {}", JSON.toJSONString(request));
        promotionPlanService.deleteAdPlan(request);
    }

}