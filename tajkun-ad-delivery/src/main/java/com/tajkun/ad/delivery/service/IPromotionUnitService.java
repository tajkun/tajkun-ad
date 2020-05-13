package com.tajkun.ad.delivery.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tajkun.ad.common.exception.AdException;
import com.tajkun.ad.delivery.pojo.unit_dimension.UnitDistrict;
import com.tajkun.ad.delivery.pojo.unit_dimension.UnitKeyword;
import com.tajkun.ad.delivery.vo.*;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-04-23 10:35
 **/
public interface IPromotionUnitService{

    PromotionUnitResponse createPromotionUnit(PromotionUnitRequest request) throws AdException;

    // 推广单元限制维度
    UnitKeywordResponse createUnitKeyword(UnitKeywordRequest request) throws AdException;

    UnitInterestResponse createUnitInterest(UnitInterestRequest request) throws AdException;

    UnitDistrictResponse createUnitDistrict(UnitDistrictRequest request) throws AdException;

    // 推广单元与创意关联
    CreativeUnitResponse createCreativeUnit(CreativeUnitRequest request) throws AdException;
}