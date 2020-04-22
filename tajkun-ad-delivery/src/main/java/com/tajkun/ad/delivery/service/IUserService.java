package com.tajkun.ad.delivery.service;

import com.tajkun.ad.common.exception.AdException;
import com.tajkun.ad.delivery.vo.CreateUserRequest;
import com.tajkun.ad.delivery.vo.CreateUserResponse;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-04-22 20:36
 **/
public interface IUserService {

    // 创建用户
    CreateUserResponse createUser(CreateUserRequest request) throws AdException;
}