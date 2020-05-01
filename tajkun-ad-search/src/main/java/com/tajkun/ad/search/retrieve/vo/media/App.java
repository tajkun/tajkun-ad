package com.tajkun.ad.search.retrieve.vo.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: tajkun-ad
 * @description: 终端应用信息
 * @author: Jiakun
 * @create: 2020-05-01 22:11
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class App {

    // 应用编码
    private String appCode;
    // 应用名称
    private String appName;
    // 应用包名
    private String packageName;
    // activity名称 应用的请求页面
    private String activityName;

}