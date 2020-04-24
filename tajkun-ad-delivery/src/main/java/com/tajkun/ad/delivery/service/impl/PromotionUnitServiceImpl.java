package com.tajkun.ad.delivery.service.impl;

import com.tajkun.ad.common.exception.AdException;
import com.tajkun.ad.delivery.constant.Constants;
import com.tajkun.ad.delivery.pojo.PromotionPlan;
import com.tajkun.ad.delivery.pojo.PromotionUnit;
import com.tajkun.ad.delivery.pojo.unit_dimension.CreativeUnit;
import com.tajkun.ad.delivery.pojo.unit_dimension.UnitDistrict;
import com.tajkun.ad.delivery.pojo.unit_dimension.UnitInterest;
import com.tajkun.ad.delivery.pojo.unit_dimension.UnitKeyword;
import com.tajkun.ad.delivery.repository.CreativeRepository;
import com.tajkun.ad.delivery.repository.PromotionPlanRepository;
import com.tajkun.ad.delivery.repository.PromotionUnitRepository;
import com.tajkun.ad.delivery.repository.unit_dimension.CreativeUnitRepository;
import com.tajkun.ad.delivery.repository.unit_dimension.UnitDistrictRepository;
import com.tajkun.ad.delivery.repository.unit_dimension.UnitInterestRepository;
import com.tajkun.ad.delivery.repository.unit_dimension.UnitKeywordRepository;
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

    private final PromotionPlanRepository planRepository;

    private final PromotionUnitRepository unitRepository;

    private final UnitKeywordRepository unitKeywordRepository;

    private final UnitDistrictRepository unitDistrictRepository;

    private final UnitInterestRepository unitInterestRepository;

    private final CreativeRepository creativeRepository;

    private final CreativeUnitRepository creativeUnitRepository;

    @Autowired
    public PromotionUnitServiceImpl(PromotionPlanRepository planRepository, PromotionUnitRepository unitRepository, UnitKeywordRepository unitKeywordRepository, UnitDistrictRepository unitDistrictRepository, UnitInterestRepository unitInterestRepository, CreativeRepository creativeRepository, CreativeUnitRepository creativeUnitRepository) {
        this.planRepository = planRepository;
        this.unitRepository = unitRepository;
        this.unitKeywordRepository = unitKeywordRepository;
        this.unitDistrictRepository = unitDistrictRepository;
        this.unitInterestRepository = unitInterestRepository;
        this.creativeRepository = creativeRepository;
        this.creativeUnitRepository = creativeUnitRepository;
    }

    @Override
    @Transactional
    public PromotionUnitResponse createPromotionUnit(PromotionUnitRequest request) throws AdException {

        if (!request.createValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        Optional<PromotionPlan> promotionPlan = planRepository.findById(request.getPlanId());
        if (!promotionPlan.isPresent()) {
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }

        PromotionUnit oldUnit = unitRepository.findByPlanIdAndUnitName(request.getPlanId(),request.getUnitName());
        if (oldUnit != null) {
            throw new AdException(Constants.ErrorMsg.SAME_NAME_UNIT_ERROR);
        }

        PromotionUnit newUnit = unitRepository.save(
                new PromotionUnit(request.getPlanId(), request.getUnitName(),
                        request.getPositionType(), request.getBudget())
        );

        return new PromotionUnitResponse(newUnit.getId(), newUnit.getUnitName());
    }

    @Override
    public UnitKeywordResponse createUnitKeyword(UnitKeywordRequest request) throws AdException {
        // 获取要创建的单元维度中的单元id
        List<Long> unitIds = request.getUnitKeywordVos().stream()
                .map(UnitKeywordRequest.UnitKeywordVO::getUnitId)
                .collect(Collectors.toList());
        // 判断这些单元id是否存在
        if (!isRelatedUnitExist(unitIds)) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        // 存放创建完成的单元维度的id
        List<Long> ids = Collections.emptyList();
        // 存放要创建的单元维度
        List<UnitKeyword> unitKeywords = new ArrayList<>();
        // 创建单元维度
        if (!CollectionUtils.isEmpty(request.getUnitKeywordVos())) {
            request.getUnitKeywordVos().forEach(unitKeywordVo -> unitKeywords.add(
                    new UnitKeyword(unitKeywordVo.getUnitId(), unitKeywordVo.getKeyword())
            ));
            ids = unitKeywordRepository.saveAll(unitKeywords).stream()
                    .map(UnitKeyword::getUnitId)
                    .collect(Collectors.toList());
        }
        return new UnitKeywordResponse(ids);
    }

    @Override
    public UnitInterestResponse createUnitInterest(UnitInterestRequest request)
            throws AdException {

        List<Long> unitIds = request.getUnitInterestVos().stream()
                .map(UnitInterestRequest.UnitInterestVO::getUnitId)
                .collect(Collectors.toList());
        if (!isRelatedUnitExist(unitIds)) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        List<Long> ids = Collections.emptyList();
        List<UnitInterest> unitInterests = new ArrayList<>();
        if (!CollectionUtils.isEmpty(request.getUnitInterestVos())) {
            request.getUnitInterestVos().forEach(unitInterestVo -> unitInterests.add(
                    new UnitInterest(unitInterestVo.getUnitId(), unitInterestVo.getInterestTag())
            ));
            ids = unitInterestRepository.saveAll(unitInterests).stream()
                    .map(UnitInterest::getId)
                    .collect(Collectors.toList());
        }
        return new UnitInterestResponse(ids);
    }


    @Override
    public UnitDistrictResponse createUnitDistrict(UnitDistrictRequest request) throws AdException {

        List<Long> unitIds = request.getUnitDistrictVos().stream()
                .map(UnitDistrictRequest.UnitDistrictVO::getUnitId)
                .collect(Collectors.toList());
        if (!isRelatedUnitExist(unitIds)) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        List<UnitDistrict> unitDistricts = new ArrayList<>();
        request.getUnitDistrictVos().forEach(unitDistrictVo -> unitDistricts.add(
                new UnitDistrict(unitDistrictVo.getUnitId(), unitDistrictVo.getProvince(),
                        unitDistrictVo.getCity())
        ));
        List<Long> ids = unitDistrictRepository.saveAll(unitDistricts).stream()
                .map(UnitDistrict::getId)
                .collect(Collectors.toList());

        return new UnitDistrictResponse(ids);
    }

    @Override
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

        List<CreativeUnit> creativeUnits = new ArrayList<>();
        request.getCreativeUnitItems().forEach(creativeUnitItem -> creativeUnits.add(
                new CreativeUnit(creativeUnitItem.getCreativeId(), creativeUnitItem.getUnitId())
        ));
        List<Long> ids = creativeUnitRepository.saveAll(creativeUnits).stream()
                .map(CreativeUnit::getId)
                .collect(Collectors.toList());

        return new CreativeUnitResponse(ids);
    }


    // 推广单元限制维度统一验证方法
    private boolean isRelatedUnitExist(List<Long> unitIds) {
        if (CollectionUtils.isEmpty(unitIds)) {
            return false;
        }
        // 是否有重复
        return unitRepository.findAllById(unitIds).size() == new HashSet<>(unitIds).size();
    }

    private boolean isRelatedCreativeExist(List<Long> creativeIds) {
        if (CollectionUtils.isEmpty(creativeIds)) {
            return false;
        }
        return creativeRepository.findAllById(creativeIds).size() == new HashSet<>(creativeIds).size();
    }

}