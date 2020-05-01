package com.tajkun.ad.search.retrieve.vo.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @program: tajkun-ad
 * @description: 广告位
 * @author: Jiakun
 * @create: 2020-05-01 22:04
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdSlot {

    // 广告位编码
    private String adSlotCode;
    // 流量类型
    private Integer positionType;
    // 广告位宽度
    private Integer width;
    // 广告位高度
    private Integer height;
    // 广告物料类型，图片、视频等
    private List<Integer> type;
    // 最低出价
    private Integer minCpm;

}