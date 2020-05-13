package com.tajkun.ad.delivery.service;

import com.tajkun.ad.common.exception.AdException;
import com.tajkun.ad.delivery.Application;
import com.tajkun.ad.delivery.vo.PromotionPlanGetRequest;
import com.tajkun.ad.delivery.vo.PromotionPlanRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-05-03 17:03
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class PlanServiceTest {

    @Autowired
    private IPromotionPlanService planService;

    @Test
    public void testGetPlan() throws AdException {
        System.out.println("测试开始-------------");
        System.out.println(planService.getPromotionPlanByIds(
                new PromotionPlanGetRequest(15L, Collections.singletonList(10L))
        ));
        System.out.println("测试结束-------------");
    }

    @Test
    public void testCreatePromotionPlan() throws AdException {
        System.out.println("测试开始-------------");
        System.out.println(planService.createPromotionPlan(
                new PromotionPlanRequest(11L, 15L, "abc计划1",
                        "2018-11-28", "2019-11-28")
        ));
        System.out.println("测试结束-------------");
    }

    @Test
    public void testUpdatePromotionPlan() throws AdException {
        System.out.println("测试开始-------------");
        System.out.println(planService.updatePromotionPlan(
                new PromotionPlanRequest(17L, 15L, "abc计划22",
                        "2018-11-28", "2019-11-28")
        ));
        System.out.println("测试结束-------------");
    }

    @Test
    public void testDeletePromotionPlan() throws AdException {
        System.out.println("测试开始-------------");
        planService.deletePromotionPlan(
                new PromotionPlanRequest(18L, 15L,
                        null, null, null)
        );
        System.out.println("测试结束-------------");
    }

}