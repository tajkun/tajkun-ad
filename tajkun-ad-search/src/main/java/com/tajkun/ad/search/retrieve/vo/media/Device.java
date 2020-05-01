package com.tajkun.ad.search.retrieve.vo.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: tajkun-ad
 * @description: 设备
 * @author: Jiakun
 * @create: 2020-05-01 22:19
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Device {

    // 设备id
    private String deviceCode;
    // 设备mac地址
    private String mac;
    // ip
    private String ip;
    // 机型编码
    private String model;
    // 分辨率尺寸
    private String displaySize;
    // 屏幕尺寸
    private String screenSize;
    // 设备序列号
    private String serialName;

}