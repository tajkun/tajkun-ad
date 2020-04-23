package com.tajkun.ad.delivery.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-04-23 11:15
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnitDistrictResponse {

    private List<Long> ids;
}