package com.tajkun.ad.search.index;

import com.alibaba.fastjson.JSON;
import com.tajkun.ad.common.export.ExportConstant;
import com.tajkun.ad.common.export.table.*;
import com.tajkun.ad.search.handler.LevelDataHandler;
import com.tajkun.ad.search.mysql.constant.OpType;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: tajkun-ad
 * @description: 读取文件创建全量索引
 * @author: Jiakun
 * @create: 2020-04-25 13:17
 **/
@Component
@DependsOn("dataTable")
public class IndexFileLoader {

    // 全量索引加载，检索系统启动时执行
    @PostConstruct
    public void init() {
        // 顺序2 3 4
        List<String> planStrings = loadExportData(
                String.format("%s%s", ExportConstant.DATA_ROOT_DIR, ExportConstant.PROMOTION_PLAN));
        planStrings.forEach(plan -> LevelDataHandler.handleLevel2(
                JSON.parseObject(plan, PlanTable.class),
                OpType.ADD
        ));

        List<String> creativeStrings = loadExportData(
                String.format("%s%s", ExportConstant.DATA_ROOT_DIR, ExportConstant.CREATIVE));
        creativeStrings.forEach(plan -> LevelDataHandler.handleLevel2(
                JSON.parseObject(plan, CreativeTable.class),
                OpType.ADD
        ));

        List<String> unitStrings = loadExportData(
                String.format("%s%s", ExportConstant.DATA_ROOT_DIR, ExportConstant.PROMOTION_UNIT));
        unitStrings.forEach(plan -> LevelDataHandler.handleLevel3(
                JSON.parseObject(plan, UnitTable.class),
                OpType.ADD
        ));

        List<String> creativeUnitStrings = loadExportData(
                String.format("%s%s", ExportConstant.DATA_ROOT_DIR, ExportConstant.CREATIVE_UNIT));
        creativeUnitStrings.forEach(plan -> LevelDataHandler.handleLevel3(
                JSON.parseObject(plan, CreativeUnitTable.class),
                OpType.ADD
        ));

        List<String> unitDistrictStrings = loadExportData(
                String.format("%s%s", ExportConstant.DATA_ROOT_DIR, ExportConstant.UNIT_DISTRICT));
        unitDistrictStrings.forEach(plan -> LevelDataHandler.handleLevel4(
                JSON.parseObject(plan, UnitDistrictTable.class),
                OpType.ADD
        ));

        List<String> unitInterestStrings = loadExportData(
                String.format("%s%s", ExportConstant.DATA_ROOT_DIR, ExportConstant.UNIT_INTEREST));
        unitInterestStrings.forEach(plan -> LevelDataHandler.handleLevel4(
                JSON.parseObject(plan, UnitInterestTable.class),
                OpType.ADD
        ));

        List<String> unitKeywordStrings = loadExportData(
                String.format("%s%s", ExportConstant.DATA_ROOT_DIR, ExportConstant.UNIT_KEYWORD));
        unitKeywordStrings.forEach(plan -> LevelDataHandler.handleLevel4(
                JSON.parseObject(plan, UnitKeywordTable.class),
                OpType.ADD
        ));
    }

    // 读取数据文件到List<String>
    private List<String> loadExportData(String fileName) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(fileName))){
                return reader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}