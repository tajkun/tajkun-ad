package com.tajkun.ad.search.index.district;

import com.tajkun.ad.search.index.IndexAware;
import com.tajkun.ad.search.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-04-24 21:19
 **/
@Slf4j
@Component
public class DistrictIndex implements IndexAware<String, Set<Long>> {

    // 反向索引
    private static Map<String, Set<Long>> districtUnitMap;
    // 正向索引
    private static Map<Long, Set<String>> unitDistrictMap;

    static {
        districtUnitMap = new ConcurrentHashMap<>();
        unitDistrictMap = new ConcurrentHashMap<>();
    }

    @Override
    public Set<Long> get(String key) {
        return districtUnitMap.get(key);
    }

    @Override
    public void add(String key, Set<Long> value) {
        log.info("before add: {}", unitDistrictMap);
        Set<Long> unitIds = CommonUtils.getorCreate(
                key,
                districtUnitMap,
                ConcurrentSkipListSet::new
        );
        unitIds.addAll(value);

        for (Long unitId : value) {
            Set<String> districts = CommonUtils.getorCreate(
                    unitId,
                    unitDistrictMap,
                    ConcurrentSkipListSet::new
            );
            districts.add(key);
        }
        log.info("after add: {}", unitDistrictMap);
    }

    @Override
    public void update(String key, Set<Long> value) {
        log.error("district index can not support update");
    }

    @Override
    public void delete(String key, Set<Long> value) {
        log.info("before delete: {}", unitDistrictMap);
        // 避免全部删除
        Set<Long> unitIds = CommonUtils.getorCreate(
                key,
                districtUnitMap,
                ConcurrentSkipListSet::new
        );
        unitIds.removeAll(value);

        for (Long unitId : value) {
            Set<String> districts = CommonUtils.getorCreate(
                    unitId,
                    unitDistrictMap,
                    ConcurrentSkipListSet::new);
            districts.remove(key);
        }
        log.info("after delete: {}", unitDistrictMap);
    }

    public boolean match(Long unitId, List<String> districts) {
        if (unitDistrictMap.containsKey(unitId)
                && CollectionUtils.isNotEmpty(unitDistrictMap.get(unitId))) {
            Set<String> interests = unitDistrictMap.get(unitId);
            return CollectionUtils.isSubCollection(districts, interests);
        }
        return false;
    }
}