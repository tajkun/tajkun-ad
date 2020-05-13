package com.tajkun.ad.delivery.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @program: tajkun-ad
 * @description: 广告创意
 * @author: Jiakun
 * @create: 2020-04-22 12:38
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName(value = "ad_creative")
public class Creative {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "name")
    private String name;

    // 视频 图片 文字...
    @TableField(value = "type")
    private Integer type;

    // 物料类型，比如图片可以是bmp, jpg等
    @TableField(value = "material_type")
    private Integer materialType;

    @TableField(value = "height")
    private Integer height;

    @TableField(value = "width")
    private Integer width;

    // 物料大小
    @TableField(value = "size")
    private Long size;

    // 持续时长，只有视频不为0
    @TableField(value = "duration")
    private Integer duration;

    // 审核状态
    @TableField(value = "audit_status")
    private Integer auditStatus;

    @TableField(value = "user_id")
    private Long userId;

    // 物料路径
    @TableField(value = "url")
    private String url;

    @TableField(value = "create_time")
    private Date createTime;

    @TableField(value = "update_time")
    private Date updateTime;

}