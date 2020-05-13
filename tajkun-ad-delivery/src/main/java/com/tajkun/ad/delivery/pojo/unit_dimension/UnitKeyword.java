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
 * @description: 关键词维度
 * @author: Jiakun
 * @create: 2020-04-22 11:34
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName(value = "ad_unit_keyword")
public class UnitKeyword {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "unit_id")
    private Long unitId;

    @TableField(value = "keyword")
    private String keyword;

    public UnitKeyword(Long unitId, String keyword) {
        this.unitId = unitId;
        this.keyword = keyword;
    }

}