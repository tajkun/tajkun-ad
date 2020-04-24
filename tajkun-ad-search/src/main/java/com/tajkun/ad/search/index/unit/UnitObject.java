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
}