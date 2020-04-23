package com.tajkun.ad.delivery.controller;

import com.alibaba.fastjson.JSON;
import com.tajkun.ad.common.exception.AdException;
import com.tajkun.ad.delivery.service.IUserService;
import com.tajkun.ad.delivery.vo.CreateUserRequest;
import com.tajkun.ad.delivery.vo.CreateUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-04-23 14:56
 **/
@Slf4j
@RestController  // 返回json类型数据
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create/user")
    public CreateUserResponse createUser(@RequestBody CreateUserRequest request) throws AdException {

        log.info("tajkun-ad-delivery: createUser -> {}", JSON.toJSONString(request));
        return userService.createUser(request);
    }

}