package com.tajkun.ad.delivery.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-04-23 11:02
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnitKeywordRequest {

    private List<UnitKeywordVo> unitKeywordVos;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UnitKeywordVo {

        private Long unitId;
        private String keyword;
    }
}