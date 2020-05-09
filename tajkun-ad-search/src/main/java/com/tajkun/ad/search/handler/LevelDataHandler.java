package com.tajkun.ad.search.handler;

import com.alibaba.fastjson.JSON;
import com.tajkun.ad.binlogrel.constant.OpType;
import com.tajkun.ad.common.export.table.*;
import com.tajkun.ad.search.index.DataTable;
import com.tajkun.ad.search.index.IndexAware;
import com.tajkun.ad.search.index.creativeunit.CreativeUnitObject;
import com.tajkun.ad.search.index.creativeunit.CreativeUnitIndex;
import com.tajkun.ad.search.index.creative.CreativeIndex;
import com.tajkun.ad.search.index.creative.CreativeObject;
import com.tajkun.ad.search.index.district.DistrictIndex;
import com.tajkun.ad.search.index.interest.InterestIndex;
import com.tajkun.ad.search.index.keyword.KeywordIndex;
import com.tajkun.ad.search.index.plan.PlanIndex;
import com.tajkun.ad.search.index.plan.PlanObject;
import com.tajkun.ad.search.index.unit.UnitIndex;
import com.tajkun.ad.search.index.unit.UnitObject;
import com.tajkun.ad.search.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @program: tajkun-ad
 * @description: 索引之间存在着层级划分，即依赖关系划分，用户与推广计划为顶层 暂时不考虑
 * 加载全量索引是增量索引“添加”的一种特殊实现，来统一加载全量索引与增量索引代码的统一
 * @author: Jiakun
 * @create: 2020-04-25 11:08
 **/
@Slf4j
public class LevelDataHandler {

    public static void handleLevel2(PlanTable planTable, OpType type) {

        PlanObject planObject = new PlanObject(
                planTable.getPlanId(), planTable.getUserId(), planTable.getPlanStatus(),
                planTable.getStartDate(), planTable.getEndDate()
        );
        handleBinlogEvent(DataTable.of(PlanIndex.class), planObject.getPlanId(), planObject, type);
    }

    public static void handleLevel2(CreativeTable creativeTable, OpType type) {

        CreativeObject creativeObject = new CreativeObject(
                creativeTable.getId(), creativeTable.getName(), creativeTable.getType(),
                creativeTable.getMaterialType(), creativeTable.getHeight(),
                creativeTable.getWidth(), creativeTable.getAuditStatus(), creativeTable.getUrl()
        );
        handleBinlogEvent(DataTable.of(CreativeIndex.class), creativeObject.getId(), creativeObject, type);
    }

    public static void handleLevel3(UnitTable unitTable, OpType type) {

        PlanObject planObject = DataTable.of(PlanIndex.class).get(unitTable.getPlanId());
        if (null == planObject) {
            log.error("handleLevel3 found PlanObject error : {}", unitTable.getPlanId());
            return;
        }
        UnitObject unitObject = new UnitObject(
                unitTable.getUnitId(), unitTable.getUnitStatus(), unitTable.getPositionType(),
                unitTable.getPlanId(), planObject
        );

        handleBinlogEvent(DataTable.of(UnitIndex.class), unitTable.getUnitId(), unitObject, type);
    }

    public static void handleLevel3(CreativeUnitTable creativeUnitTable, OpType type) {

        if (type == OpType.UPDATE) {
            log.error("creativeUnitIndex not support update ");
            return;
        }

        UnitObject unitObject = DataTable.of(UnitIndex.class).get(creativeUnitTable.getUnitId());
        CreativeObject creativeObject = DataTable.of(CreativeIndex.class).get(creativeUnitTable.getCreativeId());
        if (null == unitObject || null == creativeObject) {
            log.error("creativeUnitTable index error : {}", JSON.toJSONString(creativeUnitTable));
            return;
        }

        CreativeUnitObject creativeUnitObject = new CreativeUnitObject(
               creativeUnitTable.getCreativeId(), creativeUnitTable.getUnitId()
        );
        handleBinlogEvent(DataTable.of(CreativeUnitIndex.class),
                CommonUtils.stringConcat(creativeUnitObject.getCreativeId().toString(), creativeUnitObject.getUnitId().toString()),
                creativeUnitObject, type);
    }

    public static void handleLevel4(UnitDistrictTable unitDistrictTable, OpType type) {

        if (type == OpType.UPDATE) {
            log.error("district index can not support update");
            return;
        }

        UnitObject unitObject = DataTable.of(UnitIndex.class).get(unitDistrictTable.getUnitId());
        if (unitObject == null) {
            log.error("UnitDistrictTable index error: {}", unitDistrictTable.getUnitId());
            return;
        }

        String key = CommonUtils.stringConcat(unitDistrictTable.getProvince(), unitDistrictTable.getCity());
        Set<Long> value = new HashSet<>(
                Collections.singleton(unitDistrictTable.getUnitId())
        );
        handleBinlogEvent(DataTable.of(DistrictIndex.class), key, value, type);
    }

    public static void handleLevel4(UnitInterestTable unitInterestTable, OpType type) {

        if (type == OpType.UPDATE) {
            log.error("interest index can not support update");
            return;
        }

        UnitObject unitObject = DataTable.of(UnitIndex.class).get(unitInterestTable.getUnitId());
        if (unitObject == null) {
            log.error("UnitInterestTable index error: {}", unitInterestTable.getUnitId());
            return;
        }

        Set<Long> value = new HashSet<>(
                Collections.singleton(unitInterestTable.getUnitId())
        );
        handleBinlogEvent(DataTable.of(InterestIndex.class), unitInterestTable.getInterestTag(), value, type);
    }

    public static void handleLevel4(UnitKeywordTable unitKeywordTable, OpType type) {

        if (type == OpType.UPDATE) {
            log.error("keyword index can not support update");
            return;
        }

        UnitObject unitObject = DataTable.of(UnitIndex.class).get(unitKeywordTable.getUnitId());
        if (unitObject == null) {
            log.error("UnitKeywordTable index error: {}", unitKeywordTable.getUnitId());
            return;
        }

        Set<Long> value = new HashSet<>(
                Collections.singleton(unitKeywordTable.getUnitId())
        );
        handleBinlogEvent(DataTable.of(KeywordIndex.class), unitKeywordTable.getKeyword(), value, type);
    }


    private static <K, V> void handleBinlogEvent(IndexAware<K, V> index, K key, V value, OpType type) {
        switch (type) {
            case ADD:
                index.add(key, value);
                break;
            case UPDATE:
                index.update(key, value);
                break;
            case DELETE:
                index.delete(key, value);
                break;
            default:
                break;
        }
    }


}