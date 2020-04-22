package com.tajkun.ad.delivery.constant;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-04-22 13:00
 **/
public enum CreativeType {

    IMAGE(1, "图片"),
    VIDEO(2, "视频"),
    TEXT(3, "文本");

    private int typeCode;
    private String desc;

    CreativeType(int typeCode, String desc) {
        this.typeCode = typeCode;
        this.desc = desc;
    }
}
