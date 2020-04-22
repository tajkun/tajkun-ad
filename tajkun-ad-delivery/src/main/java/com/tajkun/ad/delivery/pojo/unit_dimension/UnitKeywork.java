package com.tajkun.ad.delivery.pojo.unit_dimension;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @program: tajkun-ad
 * @description: 关键词维度
 * @author: Jiakun
 * @create: 2020-04-22 11:34
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ad_unit_keyword")
public class UnitKeywork {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "unit_id", nullable = false)
    private Long unitId;

    @Column(name = "keyword", nullable = false)
    private String keyword;

    public UnitKeywork(Long unitId, String keyword) {
        this.unitId = unitId;
        this.keyword = keyword;
    }
}