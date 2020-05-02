package com.tajkun.ad.search.index;

import lombok.Getter;

/**
 * @program: tajkun-ad
 * @description: 推广单元关联的推广计划的状态
 * @author: Jiakun
 * @create: 2020-05-02 18:17
 **/
@Getter
public enum  CommonStatus {

    VALID(1, "有效状态"),
    INVALID(0, "无效状态");

    private Integer status;
    private String desc;

    CommonStatus(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }
}