package com.tajkun.ad.search.mysql.constant;

import com.github.shyiko.mysql.binlog.event.EventType;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-04-25 11:21
 **/
public enum OpType {

    ADD,
    UPDATE,
    DELETE,
    OTHER;

    // EventType -> OpType
    public static OpType toOpType(EventType eventType) {

        switch (eventType) {
            case EXT_WRITE_ROWS:
                return ADD;
            case EXT_UPDATE_ROWS:
                return UPDATE;
            case EXT_DELETE_ROWS:
                return DELETE;
            default:
                return OTHER;
        }
    }
}
