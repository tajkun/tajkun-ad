package com.tajkun.ad.delivery.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.tajkun.ad.delivery.constant.CommonStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @program: tajkun-ad
 * @description: 推广单元
 * @author: Jiakun
 * @create: 2020-04-22 10:50
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName(value = "ad_unit")
public class PromotionUnit {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "plan_id")
    private Long planId;

    @TableField(value = "unit_name")
    private String unitName;

    @TableField(value = "unit_status")
    private Integer unitStatus;

    // 广告位类型（开屏，贴片，中贴，暂停贴...）
    @TableField(value = "position_type")
    private Integer positionType;

    @TableField(value = "budget")
    private Long budget;

    @TableField(value = "create_time")
    private Date createTime;

    @TableField(value = "update_time")
    private Date updateTime;

    public PromotionUnit(Long planId, String unitName, Integer positionType, Long budget) {
        this.planId = planId;
        this.unitName = unitName;
        this.unitStatus = CommonStatus.VALID.getStatusCode();
        this.positionType = positionType;
        this.budget = budget;
        this.createTime = new Date();
        this.updateTime = this.createTime;
    }

}