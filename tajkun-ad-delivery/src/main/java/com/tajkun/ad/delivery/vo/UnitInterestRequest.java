package com.tajkun.ad.delivery.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-04-23 11:08
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnitInterestRequest {

    private List<UnitInterestVO> unitInterestVos;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UnitInterestVO {

        private Long unitId;
        private String interestTag;
    }
}