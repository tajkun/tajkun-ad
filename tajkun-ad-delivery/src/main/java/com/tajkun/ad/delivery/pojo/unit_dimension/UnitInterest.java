package com.tajkun.ad.delivery.pojo.unit_dimension;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-04-22 11:40
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName(value = "ad_unit_interest")
public class UnitInterest {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "unit_id")
    private Long unitId;

    @TableField(value = "interest_tag")
    private String interestTag;

    public UnitInterest(Long unitId, String interestTag) {
        this.unitId = unitId;
        this.interestTag = interestTag;
    }

}