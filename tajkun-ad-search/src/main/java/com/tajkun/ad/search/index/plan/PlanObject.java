package com.tajkun.ad.search.index.plan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @program: tajkun-ad
 * @description: 索引对象定义
 * @author: Jiakun
 * @create: 2020-04-24 17:07
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanObject {

    private Long planId;
    private Long userId;
    private Integer planStatus;
    private Date startDate;
    private Date endDate;

    // 更新索引时 可能并不是更新所有字段
    public void update(PlanObject newObject) {
        if (null != newObject.getPlanId()) {
            this.planId = newObject.getPlanId();
        }

        if (null != newObject.getUserId()) {
            this.userId = newObject.getUserId();
        }

        if (null != newObject.getPlanStatus()) {
            this.planStatus = newObject.getPlanStatus();
        }

        if (null != newObject.getStartDate()) {
            this.startDate = newObject.getStartDate();
        }

        if (null != newObject.getEndDate()) {
            this.endDate = newObject.getEndDate();
        }
    }
}