package com.tajkun.ad.search.retrieve.vo;

import com.tajkun.ad.search.index.creative.CreativeObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-05-02 10:35
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchResponse {

    // map<adSlotCode, Creatives>
    public Map<String, List<Creative>> adSlot2Ads = new HashMap<>();

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Creative {

        private Long adId;
        private String adUrl;
        private Integer width;
        private Integer height;
        private Integer type;
        private Integer materialType;
        // 展示监测url
        private List<String> showMonitorUrl = Arrays.asList("www.tajkun.com","www.tajkun1.com");
        // 点击检测url
        private List<String> clickMonitorUrl = Arrays.asList("www.tajkun.com","www.tajkun1.com");
    }

    public static Creative convert(CreativeObject object) {
        Creative creative = new Creative();
        creative.setAdId(object.getId());
        creative.setAdUrl(object.getUrl());
        creative.setWidth(object.getWidth());
        creative.setHeight(object.getHeight());
        creative.setType(object.getType());
        creative.setMaterialType(object.getMaterialType());

        return creative;
    }


}