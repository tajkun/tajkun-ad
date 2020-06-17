package com.tajkun.ad.datadump.controller;

import com.tajkun.ad.datadump.service.DumpDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-06-17 11:51
 **/
@Slf4j
@RestController
public class DumpDataController {

    private final DumpDataService dumpDataService;

    @Autowired
    public DumpDataController(DumpDataService dumpDataService) {
        this.dumpDataService = dumpDataService;
    }

    @PostMapping("/datadump")
    public void dumpData() {
        log.info("tajkun-ad-datadump: 开始导出全量数据文件");
        dumpDataService.exportTableData();
    }

}