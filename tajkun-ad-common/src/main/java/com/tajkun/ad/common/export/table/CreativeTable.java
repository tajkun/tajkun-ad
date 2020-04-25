package com.tajkun.ad.common.export.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-04-25 09:29
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreativeTable {

    private Long id;
    private String name;
    private Integer type;
    private Integer materialType;
    private Integer height;
    private Integer width;
    private Integer auditStatus;
    private String  url;
}