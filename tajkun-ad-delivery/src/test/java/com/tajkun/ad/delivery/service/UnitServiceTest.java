package com.tajkun.ad.delivery.service;

import com.tajkun.ad.common.exception.AdException;
import com.tajkun.ad.delivery.Application;
import com.tajkun.ad.delivery.vo.PromotionUnitRequest;
import com.tajkun.ad.delivery.vo.UnitInterestRequest;
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
 * @create: 2020-05-03 18:00
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class UnitServiceTest {

    @Autowired
    private IPromotionUnitService unitService;

    @Test
    public void testCreatePromotionUnit() throws AdException {
        System.out.println(unitService.createPromotionUnit(
                new PromotionUnitRequest(10L, "测试推广单元11", 100, 10000L)
        ));
    }

    @Test
    public void testCreateUnitInterest() throws AdException {
        UnitInterestRequest.UnitInterestVO interestVO = new UnitInterestRequest.UnitInterestVO(
                10L, "竞技"
        );
        System.out.println(unitService.createUnitInterest(
                new UnitInterestRequest(Collections.singletonList(interestVO))
        ));
    }


}