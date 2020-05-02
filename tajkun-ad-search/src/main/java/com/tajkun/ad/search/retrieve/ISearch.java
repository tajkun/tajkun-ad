package com.tajkun.ad.search.retrieve;

import com.tajkun.ad.search.retrieve.vo.SearchRequest;
import com.tajkun.ad.search.retrieve.vo.SearchResponse;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-05-01 21:40
 **/
public interface ISearch {

    SearchResponse fetchAds(SearchRequest request);
}