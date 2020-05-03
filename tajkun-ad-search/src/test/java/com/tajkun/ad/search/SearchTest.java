package com.tajkun.ad.search;

import com.alibaba.fastjson.JSON;
import com.tajkun.ad.Application;
import com.tajkun.ad.search.retrieve.ISearch;
import com.tajkun.ad.search.retrieve.vo.SearchRequest;
import com.tajkun.ad.search.retrieve.vo.feature.DistrictFeature;
import com.tajkun.ad.search.retrieve.vo.feature.FeatureRelation;
import com.tajkun.ad.search.retrieve.vo.feature.InterestFeature;
import com.tajkun.ad.search.retrieve.vo.feature.KeywordFeature;
import com.tajkun.ad.search.retrieve.vo.media.AdSlot;
import com.tajkun.ad.search.retrieve.vo.media.App;
import com.tajkun.ad.search.retrieve.vo.media.Device;
import com.tajkun.ad.search.retrieve.vo.media.Geo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-05-03 18:26
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class SearchTest {

    @Autowired
    private ISearch search;

    @Test
    public void testFetchAds() {
        SearchRequest request = new SearchRequest();
        request.setMediaId("tajkun-ad");
        // 测试条件1
        request.setRequestInfo(new SearchRequest.RequestInfo("aaa",
                Collections.singletonList(new AdSlot(
                        "ad-x", 1, 1080, 720, Arrays.asList(1, 2), 1000)),
                buildApp(),
                buildGeo(),
                buildDevice()
                ));

        request.setFeatureInfo(
                buildFeatureInfo(
                        Arrays.asList("宝马" ,"大众"),
                        Collections.singletonList(new DistrictFeature.ProvinceAndCity("安徽省","合肥市")),
                        Arrays.asList("台球", "游泳"),
                        FeatureRelation.OR
                )
        );
        System.out.println(JSON.toJSONString(request));
        System.out.println(JSON.toJSONString(search.fetchAds(request)));

        // 测试条件2
        request.setRequestInfo(new SearchRequest.RequestInfo("aaa",
                Collections.singletonList(new AdSlot(
                        "ad-y", 1, 1080, 720, Arrays.asList(1, 2), 1000)),
                buildApp(),
                buildGeo(),
                buildDevice()
        ));

        request.setFeatureInfo(
                buildFeatureInfo(
                        Arrays.asList("宝马" ,"奥迪", "特斯拉"),
                        Collections.singletonList(new DistrictFeature.ProvinceAndCity("安徽省","合肥市")),
                        Arrays.asList("台球", "游泳"),
                        FeatureRelation.AND
                )
        );
        System.out.println(JSON.toJSONString(request));
        System.out.println(JSON.toJSONString(search.fetchAds(request)));

    }

    private App buildApp() {
        return new App("tajkun", "tajkun00",
                "tajkun11", "tajkun22");
    }

    private Geo buildGeo() {
        return new Geo((float)98.23, (float)25.33, "济南市", "济南市");
    }

    private Device buildDevice() {
        return new Device("iphone", "xxxx", "127.0.0.1", "x",
                "1080 720", "1080 720", "12345");
    }

    private SearchRequest.FeatureInfo buildFeatureInfo(List<String> keywords,
                                                       List<DistrictFeature.ProvinceAndCity> provinceAndCities,
                                                       List<String> interests,
                                                       FeatureRelation relation) {
        return new SearchRequest.FeatureInfo(
                new KeywordFeature(keywords),
                new DistrictFeature(provinceAndCities),
                new InterestFeature(interests),
                relation
        );
    }

}