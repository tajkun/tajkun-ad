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
 * @description: 创意与推广单元关联
 * @author: Jiakun
 * @create: 2020-04-22 12:55
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName(value = "creative_unit")
public class CreativeUnit {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "creative_id")
    private Long creativeId;

    @TableField(value = "unit_id")
    private Long unitId;

    public CreativeUnit(Long creativeId, Long unitId) {
        this.creativeId = creativeId;
        this.unitId = unitId;
    }

}