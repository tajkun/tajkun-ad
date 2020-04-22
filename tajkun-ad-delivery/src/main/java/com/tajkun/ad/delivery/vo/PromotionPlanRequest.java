package com.tajkun.ad.delivery.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

/**
 * @program: tajkun-ad
 * @description: 用于创建/更新/删除
 * @author: Jiakun
 * @create: 2020-04-22 21:23
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromotionPlanRequest {

    private Long id; //用于更新操作
    private Long userId;
    private String planName;
    private String startDate;
    private String endDate;

    public boolean createValidate() {
        return userId != null
                && !StringUtils.isEmpty(planName)
                && !StringUtils.isEmpty(startDate)
                && !StringUtils.isEmpty(endDate);
    }

    public boolean updateValidate() {
        return id != null && userId != null;
    }

    public boolean deleteValidate() {
        return id != null && userId != null;
    }
}