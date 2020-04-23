package com.tajkun.ad.delivery.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-04-23 11:12
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnitDistrictRequest {

    private List<UnitDistrictVo> unitDistrictVos;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UnitDistrictVo {

        private Long unitId;
        private String province;
        private String city;
    }
}