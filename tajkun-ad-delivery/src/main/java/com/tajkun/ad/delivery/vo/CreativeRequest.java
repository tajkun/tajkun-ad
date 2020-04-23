package com.tajkun.ad.delivery.vo;

import com.tajkun.ad.delivery.constant.CommonStatus;
import com.tajkun.ad.delivery.pojo.Creative;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-04-23 13:01
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreativeRequest {

    private String name;
    private Integer type;
    private Integer materialType;
    private Integer height;
    private Integer width;
    private Long size;
    private Integer duration;
    private Long userId;
    private String url;

    public Creative convertToPojo() {
        Creative creative = new Creative();
        creative.setName(name);
        creative.setType(type);
        creative.setMaterialType(materialType);
        creative.setHeight(height);
        creative.setWidth(width);
        creative.setDuration(duration);
        creative.setUserId(userId);
        creative.setUrl(url);
        creative.setAuditStatus(CommonStatus.VALID.getStatusCode());
        creative.setCreateTime(new Date());
        creative.setUpdateTime(creative.getCreateTime());

        return  creative;
    }
}