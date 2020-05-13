package com.tajkun.ad.delivery.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tajkun.ad.common.exception.AdException;
import com.tajkun.ad.delivery.constant.Constants;
import com.tajkun.ad.delivery.mapper.CreativeMapper;
import com.tajkun.ad.delivery.mapper.PromotionPlanMapper;
import com.tajkun.ad.delivery.mapper.PromotionUnitMapper;
import com.tajkun.ad.delivery.mapper.unit_dimension.CreativeUnitMapper;
import com.tajkun.ad.delivery.mapper.unit_dimension.UnitDistrictMapper;
import com.tajkun.ad.delivery.mapper.unit_dimension.UnitInterestMapper;
import com.tajkun.ad.delivery.mapper.unit_dimension.UnitKeywordMapper;
import com.tajkun.ad.delivery.pojo.PromotionPlan;
import com.tajkun.ad.delivery.pojo.PromotionUnit;
import com.tajkun.ad.delivery.pojo.unit_dimension.CreativeUnit;
import com.tajkun.ad.delivery.pojo.unit_dimension.UnitDistrict;
import com.tajkun.ad.delivery.pojo.unit_dimension.UnitInterest;
import com.tajkun.ad.delivery.pojo.unit_dimension.UnitKeyword;
import com.tajkun.ad.delivery.service.IPromotionUnitService;
import com.tajkun.ad.delivery.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-04-23 10:43
 **/
@Service
public class PromotionUnitServiceImpl implements IPromotionUnitService {

    private final PromotionPlanMapper planMapper;
    private final PromotionUnitMapper unitMapper;
    private final UnitKeywordMapper keywordMapper;
    private final UnitDistrictMapper districtMapper;
    private final UnitInterestMapper interestMapper;
    private final CreativeMapper creativeMapper;
    private final CreativeUnitMapper creativeUnitMapper;

    @Autowired
    public PromotionUnitServiceImpl(PromotionPlanMapper planMapper,
                                    PromotionUnitMapper unitMapper,
                                    UnitKeywordMapper keywordMapper,
                                    UnitDistrictMapper districtMapper,
                                    UnitInterestMapper interestMapper,
                                    CreativeMapper creativeMapper,
                                    CreativeUnitMapper creativeUnitMapper) {

        this.planMapper = planMapper;
        this.unitMapper = unitMapper;
        this.keywordMapper = keywordMapper;
        this.districtMapper = districtMapper;
        this.interestMapper = interestMapper;
        this.creativeMapper = creativeMapper;
        this.creativeUnitMapper = creativeUnitMapper;
    }

    @Override
    @Transactional
    public PromotionUnitResponse createPromotionUnit(PromotionUnitRequest request) throws AdException {

        if (!request.createValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        PromotionPlan promotionPlan = planMapper.selectById(request.getPlanId());
        if (promotionPlan == null) {
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }

        QueryWrapper<PromotionUnit> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("plan_id", request.getPlanId())
                .eq("unit_name", request.getUnitName());
        PromotionUnit oldUnit = unitMapper.selectOne(queryWrapper);
        if (oldUnit != null) {
            throw new AdException(Constants.ErrorMsg.SAME_NAME_UNIT_ERROR);
        }

        PromotionUnit newUnit = new PromotionUnit(request.getPlanId(), request.getUnitName(),
                request.getPositionType(), request.getBudget());
        unitMapper.insert(newUnit);
        return new PromotionUnitResponse(newUnit.getId(), newUnit.getUnitName());
    }

    @Override
    @Transactional
    public UnitKeywordResponse createUnitKeyword(UnitKeywordRequest request) throws AdException {

        // 获取要创建的单元维度中的单元id
        List<Long> unitIds = request.getUnitKeywordVos().stream()
                .map(UnitKeywordRequest.UnitKeywordVO::getUnitId)
                .collect(Collectors.toList());

        // 判断这些单元id是否存在
        if (!isRelatedUnitExist(unitIds)) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        // 判断是否同名
        QueryWrapper<UnitKeyword> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("unit_id", request.getUnitKeywordVos().get(0).getUnitId())
                .eq("keyword", request.getUnitKeywordVos().get(0).getKeyword());
        UnitKeyword oldKeyword = keywordMapper.selectOne(queryWrapper);
        if (oldKeyword != null){
            throw new AdException(Constants.ErrorMsg.SAME_NAME_UNIT_KEYWORD_ERROR);
        }

        // 存放创建完成的单元维度的id
        List<Long> ids = new ArrayList<>();
        // 存放要创建的单元维度
        List<UnitKeyword> unitKeywords = new ArrayList<>();

        // 创建单元维度
        if (!CollectionUtils.isEmpty(request.getUnitKeywordVos())) {
            request.getUnitKeywordVos().forEach(unitKeywordVo -> unitKeywords.add(
                    new UnitKeyword(unitKeywordVo.getUnitId(), unitKeywordVo.getKeyword())
            ));
            unitKeywords.forEach(unitKeyword -> keywordMapper.insert(unitKeyword));
            unitKeywords.forEach(unitKeyword -> ids.add(unitKeyword.getId()));
        }
        return new UnitKeywordResponse(ids);
    }

    @Override
    @Transactional
    public UnitInterestResponse createUnitInterest(UnitInterestRequest request) throws AdException {

        List<Long> unitIds = request.getUnitInterestVos().stream()
                .map(UnitInterestRequest.UnitInterestVO::getUnitId)
                .collect(Collectors.toList());
        if (!isRelatedUnitExist(unitIds)) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        // 判断是否同名
        QueryWrapper<UnitInterest> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("unit_id", request.getUnitInterestVos().get(0).getUnitId())
                .eq("interest_tag", request.getUnitInterestVos().get(0).getInterestTag());
        UnitInterest oldInterest = interestMapper.selectOne(queryWrapper);
        if (oldInterest != null){
            throw new AdException(Constants.ErrorMsg.SAME_NAME_UNIT_INTEREST_ERROR);
        }

        List<Long> ids = new ArrayList<>();
        List<UnitInterest> unitInterests = new ArrayList<>();
        if (!CollectionUtils.isEmpty(request.getUnitInterestVos())) {
            request.getUnitInterestVos().forEach(unitInterestVo -> unitInterests.add(
                    new UnitInterest(unitInterestVo.getUnitId(), unitInterestVo.getInterestTag())
            ));
            unitInterests.forEach(unitInterest -> interestMapper.insert(unitInterest));
            unitInterests.forEach(unitInterest -> ids.add(unitInterest.getId()));
        }
        return new UnitInterestResponse(ids);
    }

    @Override
    @Transactional
    public UnitDistrictResponse createUnitDistrict(UnitDistrictRequest request) throws AdException {

        List<Long> unitIds = request.getUnitDistrictVos().stream()
                .map(UnitDistrictRequest.UnitDistrictVO::getUnitId)
                .collect(Collectors.toList());
        if (!isRelatedUnitExist(unitIds)) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        // 判断是否同名
        QueryWrapper<UnitDistrict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("unit_id", request.getUnitDistrictVos().get(0).getUnitId())
                .eq("city", request.getUnitDistrictVos().get(0).getCity());
        UnitDistrict oldDistrict = districtMapper.selectOne(queryWrapper);
        if (oldDistrict != null){
            throw new AdException(Constants.ErrorMsg.SAME_NAME_UNIT_DISTRICT_ERROR);
        }

        List<UnitDistrict> unitDistricts = new ArrayList<>();
        request.getUnitDistrictVos().forEach(unitDistrictVo -> unitDistricts.add(
                new UnitDistrict(unitDistrictVo.getUnitId(), unitDistrictVo.getProvince(),
                        unitDistrictVo.getCity())
        ));
        List<Long> ids = new ArrayList<>();
        unitDistricts.forEach(unitDistrict -> districtMapper.insert(unitDistrict));
        unitDistricts.forEach(unitDistrict -> ids.add(unitDistrict.getId()));

        return new UnitDistrictResponse(ids);
    }

    @Override
    @Transactional
    public CreativeUnitResponse createCreativeUnit(CreativeUnitRequest request) throws AdException {

        List<Long> unitIds = request.getCreativeUnitItems().stream()
                .map(CreativeUnitRequest.CreativeUnitItem::getUnitId)
                .collect(Collectors.toList());
        List<Long> creativeIds = request.getCreativeUnitItems().stream()
                .map(CreativeUnitRequest.CreativeUnitItem::getCreativeId)
                .collect(Collectors.toList());
        if (!(isRelatedUnitExist(unitIds) && isRelatedCreativeExist(creativeIds))) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        // 判断是否已存在关联关系
        QueryWrapper<CreativeUnit> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("creative_id", request.getCreativeUnitItems().get(0).getCreativeId())
                .eq("unit_id", request.getCreativeUnitItems().get(0).getUnitId());
        CreativeUnit oldCreativeUnit = creativeUnitMapper.selectOne(queryWrapper);
        if (oldCreativeUnit != null) {
            throw new AdException(Constants.ErrorMsg.SAME_RELATION_CREATIVE_UNIT_ERROR);
        }

        List<CreativeUnit> creativeUnits = new ArrayList<>();
        request.getCreativeUnitItems().forEach(creativeUnitItem -> creativeUnits.add(
                new CreativeUnit(creativeUnitItem.getCreativeId(), creativeUnitItem.getUnitId())
        ));
        List<Long> ids = new ArrayList<>();
        creativeUnits.forEach(creativeUnit -> creativeUnitMapper.insert(creativeUnit));
        creativeUnits.forEach(creativeUnit -> ids.add(creativeUnit.getId()));

        return new CreativeUnitResponse(ids);
    }


    // 推广单元限制维度统一验证方法
    private boolean isRelatedUnitExist(List<Long> unitIds) {
        if (CollectionUtils.isEmpty(unitIds)) {
            return false;
        }

        // 是否有重复
        return unitMapper.selectBatchIds(unitIds).size() == new HashSet<>(unitIds).size();
    }

    private boolean isRelatedCreativeExist(List<Long> creativeIds) {
        if (CollectionUtils.isEmpty(creativeIds)) {
            return false;
        }

        return creativeMapper.selectBatchIds(creativeIds).size() == new HashSet<>(creativeIds).size();
    }

}