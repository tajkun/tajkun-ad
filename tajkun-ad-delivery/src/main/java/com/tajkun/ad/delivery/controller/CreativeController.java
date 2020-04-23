package com.tajkun.ad.delivery.controller;

import com.alibaba.fastjson.JSON;
import com.tajkun.ad.common.exception.AdException;
import com.tajkun.ad.delivery.service.ICreativeService;
import com.tajkun.ad.delivery.vo.CreativeRequest;
import com.tajkun.ad.delivery.vo.CreativeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-04-23 16:21
 **/
@Slf4j
@RestController
public class CreativeController {

    private final ICreativeService creativeService;

    @Autowired
    public CreativeController(ICreativeService creativeService) {
        this.creativeService = creativeService;
    }

    @PostMapping("/create/creative")
    public CreativeResponse createCreative(@RequestBody CreativeRequest request) throws AdException {
        log.info("tajkun-ad-delivery: createCreative -> {}", JSON.toJSONString(request));
        return creativeService.createCreative(request);
    }
    
}