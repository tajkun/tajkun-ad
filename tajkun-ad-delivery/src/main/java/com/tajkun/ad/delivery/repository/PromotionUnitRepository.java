package com.tajkun.ad.delivery.repository;

import com.tajkun.ad.delivery.pojo.PromotionUnit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-04-22 18:12
 **/
public interface PromotionUnitRepository extends JpaRepository<PromotionUnit, Long> {

    PromotionUnit findByPlanIdAndUnitName(Long planId, String unitName);

    List<PromotionUnit> findAllByUnitStatus(Integer unitStatus);
}