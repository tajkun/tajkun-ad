package com.tajkun.ad.delivery.pojo.unit_dimension;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @program: tajkun-ad
 * @description: 创意与推广单元的关联表
 * @author: Jiakun
 * @create: 2020-04-22 12:55
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "creative_unit")
public class CreativeUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "creative_id", nullable = false)
    private Long creativeId;

    @Column(name = "unit_id", nullable = false)
    private Long nuitId;

    public CreativeUnit(Long creativeId, Long nuitId) {
        this.creativeId = creativeId;
        this.nuitId = nuitId;
    }
}