package com.tajkun.ad.delivery.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tajkun.ad.common.exception.AdException;
import com.tajkun.ad.delivery.constant.Constants;
import com.tajkun.ad.delivery.mapper.CreativeMapper;
import com.tajkun.ad.delivery.mapper.UserMapper;
import com.tajkun.ad.delivery.pojo.Creative;
import com.tajkun.ad.delivery.pojo.User;
import com.tajkun.ad.delivery.service.ICreativeService;
import com.tajkun.ad.delivery.vo.CreativeRequest;
import com.tajkun.ad.delivery.vo.CreativeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-04-23 13:13
 **/
@Service
public class CreativeServiceImpl implements ICreativeService {

    private final CreativeMapper creativeMapper;
    private final UserMapper userMapper;

    @Autowired
    public CreativeServiceImpl(CreativeMapper creativeMapper, UserMapper userMapper) {
        this.creativeMapper = creativeMapper;
        this.userMapper = userMapper;
    }

    @Override
    @Transactional
    public CreativeResponse createCreative(CreativeRequest request) throws AdException {

        Creative creative = request.convertToPojo();
        // 用户是否存在
        User user = userMapper.selectById(creative.getUserId());
        if (user == null) {
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }

        // 判断是否同名
        QueryWrapper<Creative> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", creative.getUserId())
                .eq("name", creative.getName());
        Creative oldCreative = creativeMapper.selectOne(queryWrapper);
        if (oldCreative != null) {
            throw new AdException(Constants.ErrorMsg.SAME_NAME_CREATIVE_ERROR);
        }

        creativeMapper.insert(creative);
        return new CreativeResponse(creative.getId(), creative.getName());
    }

}