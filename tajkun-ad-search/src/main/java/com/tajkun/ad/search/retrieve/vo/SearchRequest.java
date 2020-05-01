package com.tajkun.ad.search.retrieve.vo;

import com.tajkun.ad.search.retrieve.vo.feature.DistrictFeature;
import com.tajkun.ad.search.retrieve.vo.feature.FeatureRelation;
import com.tajkun.ad.search.retrieve.vo.feature.InterestFeature;
import com.tajkun.ad.search.retrieve.vo.feature.KeywordFeature;
import com.tajkun.ad.search.retrieve.vo.media.AdSlot;
import com.tajkun.ad.search.retrieve.vo.media.App;
import com.tajkun.ad.search.retrieve.vo.media.Device;
import com.tajkun.ad.search.retrieve.vo.media.Geo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-05-01 21:42
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchRequest {

    // 媒体方的请求标识
    private String mediaId;
    // 请求基本信息
    private RequestInfo requestInfo;
    // 匹配信息
    private FeatureInfo featureInfo;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RequestInfo {

        private String requestId;
        // 可以一次请求多个广告位
        private List<AdSlot> adSlots;
        private App app;
        private Geo geo;
        private Device device;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FeatureInfo {

        private KeywordFeature keywordFeature;
        private DistrictFeature districtFeature;
        private InterestFeature interestFeature;
        private FeatureRelation featureRelation = FeatureRelation.AND;
    }

}