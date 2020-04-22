package com.tajkun.ad.delivery.service.impl;

import com.tajkun.ad.common.exception.AdException;
import com.tajkun.ad.delivery.constant.CommonStatus;
import com.tajkun.ad.delivery.constant.Constants;
import com.tajkun.ad.delivery.pojo.PromotionPlan;
import com.tajkun.ad.delivery.pojo.User;
import com.tajkun.ad.delivery.repository.PromotionPlanRepository;
import com.tajkun.ad.delivery.repository.UserRepository;
import com.tajkun.ad.delivery.service.IPromotionPlanService;
import com.tajkun.ad.delivery.utils.CommonUtils;
import com.tajkun.ad.delivery.vo.PromotionPlanGetRequest;
import com.tajkun.ad.delivery.vo.PromotionPlanRequest;
import com.tajkun.ad.delivery.vo.PromotionPlanResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-04-22 21:55
 **/
public class PromotionPlanServiceImpl implements IPromotionPlanService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PromotionPlanRepository promotionPlanRepository;

    @Override
    @Transactional
    public PromotionPlanResponse createPromotionPlan(PromotionPlanRequest request) throws AdException {

        if (!request.createValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        // 确保关联的用户存在
        Optional<User> user = userRepository.findById(request.getUserId());
        if (!user.isPresent()) {
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }

        PromotionPlan oldPlan = promotionPlanRepository.findByUserIdAndPlanName(
                request.getUserId(),
                request.getPlanName());
        if (oldPlan != null) {
            throw new AdException(Constants.ErrorMsg.SAME_NAME_PLAN_ERROR);
        }

        PromotionPlan newPromotionPlan = promotionPlanRepository.save(
                new PromotionPlan(request.getUserId(),
                        request.getPlanName(),
                        CommonUtils.parseStringToDate(request.getStartDate()),
                        CommonUtils.parseStringToDate(request.getEndDate()))
        );
        return new PromotionPlanResponse(newPromotionPlan.getId(), newPromotionPlan.getPlanName());
    }

    @Override
    @Transactional
    public List<PromotionPlan> getPromotionPlanByIds(PromotionPlanGetRequest request) throws AdException {

        if (!request.validate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        return promotionPlanRepository.findAllByIdInAndUserId(
                request.getIds(), request.getUserId());
    }

    @Override
    public PromotionPlanResponse updatePromotionPlan(PromotionPlanRequest request) throws AdException {

        if (!request.updateValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        PromotionPlan promotionPlan = promotionPlanRepository.findByIdAndUserId(
                request.getId(),
                request.getUserId());
        if (promotionPlan == null) {
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }

        // 可以不更新全部字段
        if (request.getPlanName() != null) {
            promotionPlan.setPlanName(request.getPlanName());
        }
        if (request.getStartDate() != null) {
            promotionPlan.setStartDate(CommonUtils.parseStringToDate(request.getStartDate()));
        }
        if (request.getEndDate() != null) {
            promotionPlan.setEndDate(CommonUtils.parseStringToDate(request.getEndDate()));
        }

        promotionPlan.setUpdateTime(new Date());
        promotionPlan = promotionPlanRepository.save(promotionPlan);
        return new PromotionPlanResponse(promotionPlan.getId(), promotionPlan.getPlanName());
    }

    @Override
    @Transactional
    public void deleteAdPlan(PromotionPlanRequest request) throws AdException {

        if (!request.deleteValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        PromotionPlan promotionPlan = promotionPlanRepository.findByIdAndUserId(
                request.getId(), request.getUserId()
        );
        if (promotionPlan == null) {
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }

        promotionPlan.setPlanStatus(CommonStatus.INVALID.getStatusCode());
        promotionPlan.setUpdateTime(new Date());
        promotionPlanRepository.save(promotionPlan);
    }
}