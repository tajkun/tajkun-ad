package com.tajkun.ad.delivery.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-04-22 12:38
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ad_creative")
public class Creative {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    private Integer type;  // 视频 图片 文字...

    // 物料类型，比如图片可以是bmp, jpg等
    @Column(name = "material_type", nullable = false)
    private Integer materialType;

    @Column(name = "height", nullable = false)
    private Integer height;

    @Column(name = "width", nullable = false)
    private Integer width;
    // 物料大小
    @Column(name = "size", nullable = false)
    private Long size;

    // 持续时长，只有视频不为0
    @Column(name = "duration", nullable = false)
    private Integer duration;

    // 审核状态
    @Column(name = "audit_status", nullable = false)
    private Integer auditStatus;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    // 物料路径
    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "create_time", nullable = false)
    private Date createTime;

    @Column(name = "update_time", nullable = false)
    private Date updateTime;


}