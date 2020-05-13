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
 * @description: todo: 地域字典
 * @author: Jiakun
 * @create: 2020-04-22 11:46
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName(value = "ad_unit_district")
public class UnitDistrict {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "unit_id")
    private Long unitId;

    @TableField(value = "province")
    private String province;

    @TableField(value = "city")
    private String city;

    public UnitDistrict(Long unitId, String province, String city) {
        this.unitId = unitId;
        this.province = province;
        this.city = city;
    }

}