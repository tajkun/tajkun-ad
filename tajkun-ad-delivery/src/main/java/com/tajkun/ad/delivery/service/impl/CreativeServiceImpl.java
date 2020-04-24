package com.tajkun.ad.delivery.service.impl;

import com.tajkun.ad.common.exception.AdException;
import com.tajkun.ad.delivery.pojo.Creative;
import com.tajkun.ad.delivery.repository.CreativeRepository;
import com.tajkun.ad.delivery.service.ICreativeService;
import com.tajkun.ad.delivery.vo.CreativeRequest;
import com.tajkun.ad.delivery.vo.CreativeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-04-23 13:13
 **/
@Service
public class CreativeServiceImpl implements ICreativeService {

    private final CreativeRepository creativeRepository;

    @Autowired
    public CreativeServiceImpl(CreativeRepository creativeRepository) {
        this.creativeRepository = creativeRepository;
    }

    @Override
    public CreativeResponse createCreative(CreativeRequest request) throws AdException {

        // todo: 参数校验
        Creative creative = creativeRepository.save(request.convertToPojo());

        return new CreativeResponse(creative.getId(), creative.getName());
    }
}