package com.tajkun.ad.delivery.constant;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-04-22 13:02
 **/
public enum CreativeMaterialType {

    JPG(1, "jpg"),
    BMP(2, "bmp"),

    MP4(3, "mp4"),
    AVI(4, "avi"),

    TXT(5, "txt");

    private int typeCode;
    private String desc;

    CreativeMaterialType(int typeCode, String desc) {
        this.typeCode = typeCode;
        this.desc = desc;
    }
}
