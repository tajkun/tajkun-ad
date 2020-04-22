package com.tajkun.ad.delivery.constant;

import lombok.Getter;

/**
 * @program: tajkun-ad
 * @description: 通用状态枚举
 * @author: Jiakun
 * @create: 2020-04-22 10:14
 **/
@Getter
public enum  CommonStatus {

    VALID(1, "有效状态"),
    INVALID(0, "无效状态");

    private Integer statusCode;
    private String desc;

    CommonStatus(Integer statusCode, String desc) {
        this.statusCode = statusCode;
        this.desc = desc;
    }
}