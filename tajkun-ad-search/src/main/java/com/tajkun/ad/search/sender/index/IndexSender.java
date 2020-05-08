package com.tajkun.ad.search.sender.index;

import com.alibaba.fastjson.JSON;
import com.tajkun.ad.common.export.table.*;
import com.tajkun.ad.search.handler.LevelDataHandler;
import com.tajkun.ad.search.index.DataLevel;
import com.tajkun.ad.search.mysql.constant.Constant;
import com.tajkun.ad.search.mysql.dto.MySqlRowData;
import com.tajkun.ad.search.sender.ISender;
import com.tajkun.ad.search.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @program: tajkun-ad
 * @description: 各层级增量数据投递：MysqlRowData -> ***table -> levelHandler
 * @author: Jiakun
 * @create: 2020-04-29 11:33
 **/
@Slf4j
@Component("indexSender")
public class IndexSender implements ISender {

    @Override
    public void sender(MySqlRowData rowData) {
        String level = rowData.getLevel();
        if (DataLevel.LEVEL2.getLevel().equals(level)) {
            level2RowData(rowData);
        } else if (DataLevel.LEVEL3.getLevel().equals(level)) {
               level3RowData(rowData);
        } else if (DataLevel.LEVEL4.getLevel().equals(level)) {
               level4RowData(rowData);
        } else {
            log.error("MysqlRowData Error : {}", JSON.toJSONString(rowData));
        }
    }

    // 第二层级增量数据投递
    private void level2RowData(MySqlRowData rowData) {

        if (rowData.getTableName().equals(Constant.AD_PLAN_TABLE_INFO.TABLE_NAME)) {
            List<PlanTable> planTables = new ArrayList<>();

            for (Map<String, String> fieldValueMap : rowData.getFieldValueMap()) {
                PlanTable planTable = new PlanTable();
                fieldValueMap.forEach((k, v) -> {
                    switch (k) {
                        case Constant.AD_PLAN_TABLE_INFO.COLUMN_ID:
                            planTable.setPlanId(Long.valueOf(v));
                            break;
                        case Constant.AD_PLAN_TABLE_INFO.COLUMN_USER_ID:
                            planTable.setUserId(Long.valueOf(v));
                            break;
                        case Constant.AD_PLAN_TABLE_INFO.COLUMN_PLAN_STATUS:
                            planTable.setPlanStatus(Integer.valueOf(v));
                            break;
                        case Constant.AD_PLAN_TABLE_INFO.COLUMN_START_DATE:
                            planTable.setStartDate(CommonUtils.parseStringDate(v));
                            break;
                        case Constant.AD_PLAN_TABLE_INFO.COLUMN_END_DATE:
                            planTable.setEndDate(CommonUtils.parseStringDate(v));
                            break;
                    }
                });
                planTables.add(planTable);
            }
            planTables.forEach(p -> LevelDataHandler.handleLevel2(p, rowData.getOpType()));

        } else if (rowData.getTableName().equals(Constant.AD_CREATIVE_TABLE_INFO.TABLE_NAME)) {
               
                List<CreativeTable> creativeTables = new ArrayList<>();
                
            for (Map<String, String> fieldValMap : rowData.getFieldValueMap()) {
                CreativeTable creativeTable = new CreativeTable();
                fieldValMap.forEach((k, v) -> {
                    switch (k) {
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_ID:
                            creativeTable.setId(Long.valueOf(v));
                            break;
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_TYPE:
                            creativeTable.setType(Integer.valueOf(v));
                            break;
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_MATERIAL_TYPE:
                            creativeTable.setMaterialType(Integer.valueOf(v));
                            break;
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_HEIGHT:
                            creativeTable.setHeight(Integer.valueOf(v));
                            break;
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_WIDTH:
                            creativeTable.setWidth(Integer.valueOf(v));
                            break;
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_AUDIT_STATUS:
                            creativeTable.setAuditStatus(Integer.valueOf(v));
                            break;
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_URL:
                            creativeTable.setUrl(v);
                            break;
                    }
                });
                creativeTables.add(creativeTable);
            }
            creativeTables.forEach(c -> LevelDataHandler.handleLevel2(c, rowData.getOpType()));
        }
    }

    // 第三层级增量数据投递
    private void level3RowData(MySqlRowData rowData) {

        if (rowData.getTableName().equals(Constant.AD_UNIT_TABLE_INFO.TABLE_NAME)) {
            List<UnitTable> unitTables = new ArrayList<>();

            for (Map<String, String> fieldValMap : rowData.getFieldValueMap()) {
                UnitTable unitTable = new UnitTable();
                fieldValMap.forEach((k, v) -> {
                    switch (k) {
                        case Constant.AD_UNIT_TABLE_INFO.COLUMN_ID:
                            unitTable.setUnitId(Long.valueOf(v));
                            break;
                        case Constant.AD_UNIT_TABLE_INFO.COLUMN_UNIT_STATUS:
                            unitTable.setUnitStatus(Integer.valueOf(v));
                            break;
                        case Constant.AD_UNIT_TABLE_INFO.COLUMN_POSITION_TYPE:
                            unitTable.setPositionType(Integer.valueOf(v));
                            break;
                        case Constant.AD_UNIT_TABLE_INFO.COLUMN_PLAN_ID:
                            unitTable.setPlanId(Long.valueOf(v));
                            break;
                    }
                });
                unitTables.add(unitTable);
            }
            unitTables.forEach(u -> LevelDataHandler.handleLevel3(u, rowData.getOpType()));

        } else if (rowData.getTableName().equals(Constant.AD_CREATIVE_UNIT_TABLE_INFO.TABLE_NAME)) {
                
            List<CreativeUnitTable> creativeUnitTables = new ArrayList<>();

            for (Map<String, String> fieldValueMap : rowData.getFieldValueMap()) {
                CreativeUnitTable creativeUnitTable = new CreativeUnitTable();
                fieldValueMap.forEach((k, v) -> {
                    switch (k) {
                        case Constant.AD_CREATIVE_UNIT_TABLE_INFO.COLUMN_CREATIVE_ID:
                            creativeUnitTable.setCreativeId(Long.valueOf(v));
                            break;
                        case Constant.AD_CREATIVE_UNIT_TABLE_INFO.COLUMN_UNIT_ID:
                            creativeUnitTable.setUnitId(Long.valueOf(v));
                            break;
                    }
                });
                creativeUnitTables.add(creativeUnitTable);
            }
            creativeUnitTables.forEach(cu -> LevelDataHandler.handleLevel3(cu, rowData.getOpType()));
        }
    }

    // 第四层级增量数据
    private void level4RowData(MySqlRowData rowData) {

        switch (rowData.getTableName()) {
            case Constant.AD_UNIT_DISTRICT_TABLE_INFO.TABLE_NAME:

                List<UnitDistrictTable> districtTables = new ArrayList<>();
                for (Map<String, String> fieldValMap : rowData.getFieldValueMap()) {
                    UnitDistrictTable districtTable = new UnitDistrictTable();
                    fieldValMap.forEach((k, v) -> {
                        switch (k) {
                            case Constant.AD_UNIT_DISTRICT_TABLE_INFO.COLUMN_UNIT_ID:
                                districtTable.setUnitId(Long.valueOf(v));
                                break;
                            case Constant.AD_UNIT_DISTRICT_TABLE_INFO.COLUMN_PROVINCE:
                                districtTable.setProvince(v);
                                break;
                            case Constant.AD_UNIT_DISTRICT_TABLE_INFO.COLUMN_CITY:
                                districtTable.setCity(v);
                                break;
                        }
                    });
                    districtTables.add(districtTable);
                }
                districtTables.forEach(d -> LevelDataHandler.handleLevel4(d, rowData.getOpType()));
                break;

            case Constant.AD_UNIT_IT_TABLE_INFO.TABLE_NAME:

                List<UnitInterestTable> interestTables = new ArrayList<>();
                for (Map<String, String> fieldValMap : rowData.getFieldValueMap()) {
                    UnitInterestTable interestTable = new UnitInterestTable();
                    fieldValMap.forEach((k, v) -> {
                        switch (k) {
                            case Constant.AD_UNIT_IT_TABLE_INFO.COLUMN_UNIT_ID:
                                interestTable.setUnitId(Long.valueOf(v));
                                break;
                            case Constant.AD_UNIT_IT_TABLE_INFO.COLUMN_IT_TAG:
                                interestTable.setInterestTag(v);
                                break;
                        }
                    });
                    interestTables.add(interestTable);
                }
                interestTables.forEach(i -> LevelDataHandler.handleLevel4(i, rowData.getOpType()));
                break;

            case Constant.AD_UNIT_KEYWORD_TABLE_INFO.TABLE_NAME:
                
                List<UnitKeywordTable> keywordTables = new ArrayList<>();
                for (Map<String, String> fieldValMap : rowData.getFieldValueMap()) {
                    UnitKeywordTable keywordTable = new UnitKeywordTable();
                    fieldValMap.forEach((k, v) -> {
                        switch (k) {
                            case Constant.AD_UNIT_KEYWORD_TABLE_INFO.COLUMN_UNIT_ID:
                                keywordTable.setUnitId(Long.valueOf(v));
                                break;
                            case Constant.AD_UNIT_KEYWORD_TABLE_INFO.COLUMN_KEYWORD:
                                keywordTable.setKeyword(v);
                                break;
                        }
                    });
                    keywordTables.add(keywordTable);
                }
                keywordTables.forEach(k -> LevelDataHandler.handleLevel4(k, rowData.getOpType()));
                break;
        }
    }

}