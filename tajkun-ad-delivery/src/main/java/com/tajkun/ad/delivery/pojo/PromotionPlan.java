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
 * @description: 推广计划
 * @author: Jiakun
 * @create: 2020-04-22 10:35
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName(value = "ad_plan")
public class PromotionPlan {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    // 在业务层维护外键关系，而不在数据层维护
    @TableField(value = "user_id")
    private Long userId;

    @TableField(value = "plan_name")
    private String planName;

    @TableField(value = "plan_status")
    private Integer planStatus;

    @TableField(value = "start_date")
    private Date startDate;

    @TableField(value = "end_date")
    private Date endDate;

    @TableField(value = "create_time")
    private Date createTime;

    @TableField(value = "update_time")
    private Date updateTime;

    public PromotionPlan(Long userId, String planName, Date startDate, Date endDate) {
        this.userId = userId;
        this.planName = planName;
        this.planStatus = CommonStatus.VALID.getStatusCode();
        this.startDate = startDate;
        this.endDate = endDate;
        this.createTime = new Date();
        this.updateTime = this.createTime;
    }

}