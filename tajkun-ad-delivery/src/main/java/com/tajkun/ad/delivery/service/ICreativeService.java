package com.tajkun.ad.delivery.service;

import com.tajkun.ad.common.exception.AdException;
import com.tajkun.ad.delivery.vo.CreativeRequest;
import com.tajkun.ad.delivery.vo.CreativeResponse;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-04-23 13:00
 **/
public interface ICreativeService {

    CreativeResponse createCreative(CreativeRequest request) throws AdException;
}