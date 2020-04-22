package com.tajkun.ad.delivery.service;

import com.tajkun.ad.common.exception.AdException;
import com.tajkun.ad.delivery.pojo.PromotionPlan;
import com.tajkun.ad.delivery.vo.PromotionPlanGetRequest;
import com.tajkun.ad.delivery.vo.PromotionPlanRequest;
import com.tajkun.ad.delivery.vo.PromotionPlanResponse;

import java.util.List;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-04-22 21:22
 **/
public interface IPromotionPlanService {

    // 创建推广计划
    PromotionPlanResponse createPromotionPlan(PromotionPlanRequest request) throws AdException;

    // 获取推广计划
    List<PromotionPlan> getPromotionPlanByIds(PromotionPlanGetRequest request) throws AdException;

    // 更新推广计划
    PromotionPlanResponse updatePromotionPlan(PromotionPlanRequest request) throws AdException;

    // 删除推广计划
    void deleteAdPlan(PromotionPlanRequest request) throws AdException;
}