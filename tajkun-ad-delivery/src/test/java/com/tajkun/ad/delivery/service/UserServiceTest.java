package com.tajkun.ad.delivery.service;

import com.tajkun.ad.common.exception.AdException;
import com.tajkun.ad.delivery.Application;
import com.tajkun.ad.delivery.pojo.User;
import com.tajkun.ad.delivery.vo.CreateUserRequest;
import com.tajkun.ad.delivery.vo.PromotionUnitRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-04-24 14:13
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class UserServiceTest {

    @Autowired
    private IUserService userService;

    @Test
    public void testCreatePromotionUnit() throws AdException {
        System.out.println("测试开始-------------");
        System.out.println(userService.createUser(
                new CreateUserRequest("jk")
        ));
        System.out.println("测试结束-------------");

    }
}