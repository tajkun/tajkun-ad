package com.tajkun.ad.delivery.service;

import com.tajkun.ad.common.exception.AdException;
import com.tajkun.ad.delivery.Application;
import com.tajkun.ad.delivery.pojo.unit_dimension.UnitDistrict;
import com.tajkun.ad.delivery.vo.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        System.out.println("测试开始-------------");
        System.out.println(unitService.createPromotionUnit(
                new PromotionUnitRequest(10L, "测试推广单元", 100, 10000L)
        ));
        System.out.println("测试结束-------------");

    }

    @Test
    public void testCreateUnitInterest() throws AdException {
        System.out.println("测试开始-------------");
        UnitInterestRequest.UnitInterestVO interestVO = new UnitInterestRequest.UnitInterestVO(
                10L, "竞技1"
        );
        System.out.println(unitService.createUnitInterest(
                new UnitInterestRequest(Collections.singletonList(interestVO))
        ));
        System.out.println("测试结束-------------");
    }

    @Test
    public void testCreateUnitKeyword() throws AdException {
        System.out.println("测试开始-------------");
        UnitKeywordRequest.UnitKeywordVO unitKeywordVO = new UnitKeywordRequest.UnitKeywordVO(
                              10L, "大众2"
        );
        System.out.println(unitService.createUnitKeyword(
                new UnitKeywordRequest(Collections.singletonList(unitKeywordVO))
        ));
        System.out.println("测试结束-------------");
    }

    @Test
    public void testCreateUnitDistrict() throws AdException {
        System.out.println("测试开始-------------");
        UnitDistrictRequest.UnitDistrictVO unitDistrictVO = new UnitDistrictRequest.UnitDistrictVO(
                10L, "辽宁省" ,"大连市2"
        );
        System.out.println(unitService.createUnitDistrict(
                new UnitDistrictRequest(Collections.singletonList(unitDistrictVO))
        ));
        System.out.println("测试结束-------------");
    }

    @Test
    public void testCreateCreativeUnit() throws AdException {
        System.out.println("测试开始-------------");
        CreativeUnitRequest.CreativeUnitItem item = new CreativeUnitRequest.CreativeUnitItem(
                10L, 19L
        );
        CreativeUnitRequest request = new CreativeUnitRequest(Collections.singletonList(item));
        unitService.createCreativeUnit(request);
        System.out.println("测试结束-------------");
    }

}