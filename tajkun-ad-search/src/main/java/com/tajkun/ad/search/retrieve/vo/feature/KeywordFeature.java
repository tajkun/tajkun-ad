package com.tajkun.ad.search.retrieve.vo.feature;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-05-01 22:25
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeywordFeature {

    private List<String> keywords;
}