package com.tajkun.ad.delivery.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-04-23 13:26
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreativeUnitResponse {

    private List<Long> ids;
}