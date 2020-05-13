package com.tajkun.ad.delivery.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tajkun.ad.common.exception.AdException;
import com.tajkun.ad.delivery.constant.CommonStatus;
import com.tajkun.ad.delivery.constant.Constants;
import com.tajkun.ad.delivery.mapper.PromotionPlanMapper;
import com.tajkun.ad.delivery.mapper.UserMapper;
import com.tajkun.ad.delivery.pojo.PromotionPlan;
import com.tajkun.ad.delivery.pojo.User;
import com.tajkun.ad.delivery.service.IPromotionPlanService;
import com.tajkun.ad.delivery.utils.CommonUtils;
import com.tajkun.ad.delivery.vo.PromotionPlanGetRequest;
import com.tajkun.ad.delivery.vo.PromotionPlanRequest;
import com.tajkun.ad.delivery.vo.PromotionPlanResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-04-22 21:55
 **/
@Service
public class PromotionPlanServiceImpl implements IPromotionPlanService {

    private final UserMapper userMapper;
    private final PromotionPlanMapper planMapper;

    @Autowired
    public PromotionPlanServiceImpl(UserMapper userMapper, PromotionPlanMapper planMapper) {
        this.userMapper = userMapper;
        this.planMapper = planMapper;
    }

    @Override
    @Transactional
    public PromotionPlanResponse createPromotionPlan(PromotionPlanRequest request) throws AdException {

        if (!request.createValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        // 确保关联的用户存在
        User user = userMapper.selectById(request.getUserId());
        if (user == null) {
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }

        QueryWrapper<PromotionPlan> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", request.getUserId())
                .eq("plan_name", request.getPlanName());
        PromotionPlan oldPlan = planMapper.selectOne(queryWrapper);
        if (oldPlan != null) {
            throw new AdException(Constants.ErrorMsg.SAME_NAME_PLAN_ERROR);
        }

        PromotionPlan newPlan = new PromotionPlan(request.getUserId(),
                request.getPlanName(),
                CommonUtils.parseStringToDate(request.getStartDate()),
                CommonUtils.parseStringToDate(request.getEndDate()));
        planMapper.insert(newPlan);
        return new PromotionPlanResponse(newPlan.getId(), newPlan.getPlanName());
    }

    @Override
    @Transactional
    public List<PromotionPlan> getPromotionPlanByIds(PromotionPlanGetRequest request) throws AdException {

        if (!request.validate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        QueryWrapper<PromotionPlan> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id",request.getIds())
                .eq("user_id", request.getUserId());
        return planMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional
    public PromotionPlanResponse updatePromotionPlan(PromotionPlanRequest request) throws AdException {

        if (!request.updateValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        QueryWrapper<PromotionPlan> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", request.getId())
                .eq("user_id", request.getUserId());
        PromotionPlan promotionPlan = planMapper.selectOne(queryWrapper);
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
        planMapper.updateById(promotionPlan);
        return new PromotionPlanResponse(promotionPlan.getId(), promotionPlan.getPlanName());
    }

    @Override
    @Transactional
    public void deletePromotionPlan(PromotionPlanRequest request) throws AdException {

        if (!request.deleteValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        QueryWrapper<PromotionPlan> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", request.getId())
                .eq("user_id", request.getUserId());
        PromotionPlan promotionPlan = planMapper.selectOne(queryWrapper);
        if (promotionPlan == null) {
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }

        promotionPlan.setPlanStatus(CommonStatus.INVALID.getStatusCode());
        promotionPlan.setUpdateTime(new Date());
        planMapper.updateById(promotionPlan);
    }

}