package com.tajkun.ad.search.index.unit;

import com.tajkun.ad.search.index.plan.PlanObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-04-24 17:29
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnitObject {

    private Long unitId;
    private Integer unitStatus;
    private Integer positionType;
    private Long planId;

    private PlanObject planObject;

    public void update(UnitObject newObject) {

        if (null != newObject.getUnitId()) {
            this.unitId = newObject.getUnitId();
        }

        if (null != newObject.getUnitStatus()) {
            this.unitStatus = newObject.getUnitStatus();
        }

        if (null != newObject.getPositionType()) {
            this.positionType = newObject.getPositionType();
        }

        if (null != newObject.getPlanId()) {
            this.planId = newObject.getPlanId();
        }

        if (null != newObject.getPlanObject()) {
            this.planObject = newObject.getPlanObject();
        }
    }

    private static boolean isKaiPing(int positionType) {
        // 如果大于0 则匹配 提高运算速度
        return (positionType & UnitConstants.POSITION_TYPE.KAIPING) > 0;
    }

    private static boolean isTiePian(int positionType) {
        return (positionType & UnitConstants.POSITION_TYPE.TIEPIAN) > 0;
    }

    private static boolean isTiePianMiddle(int positionType) {
        return (positionType & UnitConstants.POSITION_TYPE.TIEPIAN_MIDDLE) > 0;
    }

    private static boolean isTiePianPause(int positionType) {
        return (positionType & UnitConstants.POSITION_TYPE.TIEPIAN_PAUSE) > 0;
    }

    private static boolean isTiePianPost(int positionType) {
        return (positionType & UnitConstants.POSITION_TYPE.TIEPIAN_POST) > 0;
    }

    public static boolean isAdSlotTypeOK(int adSlotType, int positionType) {
        switch (adSlotType) {
            case UnitConstants.POSITION_TYPE.KAIPING:
                return isKaiPing(positionType);
            case UnitConstants.POSITION_TYPE.TIEPIAN:
                return isTiePian(positionType);
            case UnitConstants.POSITION_TYPE.TIEPIAN_MIDDLE:
                return isTiePianMiddle(positionType);
            case UnitConstants.POSITION_TYPE.TIEPIAN_PAUSE:
                return isTiePianPause(positionType);
            case UnitConstants.POSITION_TYPE.TIEPIAN_POST:
                return isTiePianPost(positionType);
            default:
                return false;
        }
    }

}