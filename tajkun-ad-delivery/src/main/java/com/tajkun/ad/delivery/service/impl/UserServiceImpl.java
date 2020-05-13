package com.tajkun.ad.delivery.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tajkun.ad.common.exception.AdException;
import com.tajkun.ad.delivery.constant.Constants;
import com.tajkun.ad.delivery.mapper.UserMapper;
import com.tajkun.ad.delivery.pojo.User;
import com.tajkun.ad.delivery.service.IUserService;
import com.tajkun.ad.delivery.utils.CommonUtils;
import com.tajkun.ad.delivery.vo.CreateUserRequest;
import com.tajkun.ad.delivery.vo.CreateUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-04-22 20:48
 **/
@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    @Transactional
    public CreateUserResponse createUser(CreateUserRequest request) throws AdException {
        if (request.validate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", request.getUsername());
        User oldUser = userMapper.selectOne(queryWrapper);
        if (oldUser != null) {
            throw new AdException(Constants.ErrorMsg.SAME_NAME_USER_ERROR);
        }

        User newUser = new User(request.getUsername(), CommonUtils.md5(request.getUsername()));
        userMapper.insert(newUser);

        return new CreateUserResponse(newUser.getId(), newUser.getUsername(), newUser.getToken(),
                newUser.getCreateTime(), newUser.getUpdateTime());
    }

}