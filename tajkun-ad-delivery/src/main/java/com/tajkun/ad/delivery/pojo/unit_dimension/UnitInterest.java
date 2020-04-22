package com.tajkun.ad.delivery.pojo.unit_dimension;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-04-22 11:40
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ad_unit_interest")
public class UnitInterest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "unit_id", nullable = false)
    private Long unitId;

    @Column(name = "interest_tag", nullable = false)
    private String interestTag;

    public UnitInterest(Long unitId, String interestTag) {
        this.unitId = unitId;
        this.interestTag = interestTag;
    }
}