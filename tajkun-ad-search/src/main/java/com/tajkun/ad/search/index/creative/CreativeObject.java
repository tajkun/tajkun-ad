package com.tajkun.ad.search.index.creative;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-04-24 21:28
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreativeObject {

    private Long id;
    private String name;
    private Integer type;
    private Integer materialType;
    private Integer height;
    private Integer width;
    private Integer auditStatus;
    private String  url;

    public void update(CreativeObject newObject) {
        if (null != newObject.getId()) {
            this.id = newObject.getId();
        }

        if (null != newObject.getName()) {
            this.name = newObject.getName();
        }

        if (null != newObject.getType()) {
            this.type = newObject.getType();
        }

        if (null != newObject.getMaterialType()) {
            this.materialType = newObject.getMaterialType();
        }

        if (null != newObject.getHeight()) {
            this.height = newObject.getHeight();
        }

        if (null != newObject.getWidth()) {
            this.width = newObject.getWidth();
        }

        if (null != newObject.getAuditStatus()) {
            this.auditStatus = newObject.getAuditStatus();
        }

        if (null != newObject.getUrl()) {
            this.url = newObject.getUrl();
        }
    }
}