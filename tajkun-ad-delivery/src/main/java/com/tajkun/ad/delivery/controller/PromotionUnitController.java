package com.tajkun.ad.delivery.controller;

import com.alibaba.fastjson.JSON;
import com.tajkun.ad.common.exception.AdException;
import com.tajkun.ad.delivery.service.IPromotionUnitService;
import com.tajkun.ad.delivery.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-04-23 16:07
 **/
@Slf4j
@RestController
public class PromotionUnitController {

    private final IPromotionUnitService promotionUnitService;

    @Autowired
    public PromotionUnitController(IPromotionUnitService promotionUnitService) {
        this.promotionUnitService = promotionUnitService;
    }

    @PostMapping("/create/promotionUnit")
    public PromotionUnitResponse createUnit(@RequestBody PromotionUnitRequest request) throws AdException {
        log.info("tajkun-ad-delivery: createUnit -> {}", JSON.toJSONString(request));
        return promotionUnitService.createPromotionUnit(request);
    }

    @PostMapping("/create/unitKeyword")
    public UnitKeywordResponse createUnitKeyword(@RequestBody UnitKeywordRequest request) throws AdException {
        log.info("tajkun-ad-delivery: createUnitKeyword -> {}", JSON.toJSONString(request));
        return promotionUnitService.createUnitKeyword(request);
    }

    @PostMapping("/create/unitInterest")
    public UnitInterestResponse createUnitInterest(@RequestBody UnitInterestRequest request) throws AdException {
        log.info("tajkun-ad-delivery: createUnitInterest -> {}", JSON.toJSONString(request));
        return promotionUnitService.createUnitInterest(request);
    }

    @PostMapping("/create/unitDistrict")
    public UnitDistrictResponse createUnitDistrict(@RequestBody UnitDistrictRequest request) throws AdException {
        log.info("tajkun-ad-delivery: createUnitDistrict -> {}", JSON.toJSONString(request));
        return promotionUnitService.createUnitDistrict(request);
    }

    @PostMapping("/create/creativeUnit")
    public CreativeUnitResponse createCreativeUnit(@RequestBody CreativeUnitRequest request) throws AdException {
        log.info("tajkun-ad-delivery: createCreativeUnit -> {}", JSON.toJSONString(request));
        return promotionUnitService.createCreativeUnit(request);
    }
}