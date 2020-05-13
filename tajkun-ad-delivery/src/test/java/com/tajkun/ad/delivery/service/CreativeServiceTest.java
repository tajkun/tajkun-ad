package com.tajkun.ad.delivery.service;

import com.tajkun.ad.common.exception.AdException;
import com.tajkun.ad.delivery.Application;
import com.tajkun.ad.delivery.mapper.CreativeMapper;
import com.tajkun.ad.delivery.service.impl.CreativeServiceImpl;
import com.tajkun.ad.delivery.vo.CreativeRequest;
import com.tajkun.ad.delivery.vo.UnitDistrictRequest;
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
 * @create: 2020-05-13 09:18
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class CreativeServiceTest {

    @Autowired
    private ICreativeService creativeService;

    @Test
    public void testCreateCreative() throws AdException {
        System.out.println("测试开始-------------");
//        UnitDistrictRequest.UnitDistrictVO unitDistrictVO = new UnitDistrictRequest.UnitDistrictVO(
//                10L, "辽宁省" ,"大连市2"
//        );
//        System.out.println(unitService.createUnitDistrict(
//                new UnitDistrictRequest(Collections.singletonList(unitDistrictVO))
//        ));
        creativeService.createCreative(
                new CreativeRequest("hahah", 1, 1, 10, 10, 1L, 2, 15l, "11")
        );

        System.out.println("测试结束-------------");

    }
}