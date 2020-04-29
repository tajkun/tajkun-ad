package com.tajkun.ad.search.index;

import lombok.Getter;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-04-29 11:25
 **/
@Getter
public enum DataLevel {

    LEVEL2("2", "level 2"),
    LEVEL3("3", "level 3"),
    LEVEL4("4", "level 4");

    private String level;
    private String desc;

    DataLevel(String level, String desc) {
        this.level = level;
        this.desc = desc;
    }

}
