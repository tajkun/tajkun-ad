package com.tajkun.ad.delivery.pojo;

import com.tajkun.ad.delivery.constant.CommonStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
@Entity
@Table(name = "ad_plan")
public class PromotionPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    // 在业务层维护外键关系，而不在数据层维护
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "plan_name", nullable = false)
    private String planName;

    @Column(name = "plan_status", nullable = false)
    private Integer planStatus;

    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @Column(name = "create_time", nullable = false)
    private Date createTime;

    @Column(name = "update_time", nullable = false)
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