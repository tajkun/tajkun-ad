package com.tajkun.ad.delivery.repository;

import com.tajkun.ad.delivery.pojo.PromotionPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-04-22 18:04
 **/
public interface PromotionPlanRepository extends JpaRepository<PromotionPlan, Long> {

    PromotionPlan findByIdAndUserId(Long id, Long userId);

    List<PromotionPlan> findAllByIdInAndUserId(List<Long> ids, Long userId);

    PromotionPlan findByUserIdAndPlanName(Long userId, String planName);

    List<PromotionPlan> findAllByPlanStatus(Integer status);
}