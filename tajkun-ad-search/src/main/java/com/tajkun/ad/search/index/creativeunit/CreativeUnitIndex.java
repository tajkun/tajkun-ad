package com.tajkun.ad.search.index.creativeunit;

import com.tajkun.ad.search.index.IndexAware;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-04-24 21:43
 **/
@Slf4j
@Component
public class CreativeUnitIndex implements IndexAware<String, CreativeUnitObject> {

    // <creativeId-unitId, CreativeObject>
    private static Map<String, CreativeUnitObject> objectMap;
    // <createId, unitIds>
    private static Map<Long, Set<Long>> creativeUnitMap;
    // <unitId, creativeIds>
    private static Map<Long, Set<Long>> unitCreativeMap;

    static {
        objectMap = new ConcurrentHashMap<>();
        creativeUnitMap = new ConcurrentHashMap<>();
        unitCreativeMap = new ConcurrentHashMap<>();
    }

    @Override
    public CreativeUnitObject get(String key) {
        return objectMap.get(key);
    }

    @Override
    public void add(String key, CreativeUnitObject value) {
        log.info("before add: {}", objectMap);
        objectMap.put(key, value);

        Set<Long> unitIds = creativeUnitMap.get(value.getCreativeId());
        if (CollectionUtils.isEmpty(unitIds)) {
            unitIds = new ConcurrentSkipListSet<>();
            creativeUnitMap.put(value.getCreativeId(), unitIds);
        }
        unitIds.add(value.getUnitId());

        Set<Long> creativeIds = unitCreativeMap.get(value.getUnitId());
        if (CollectionUtils.isEmpty(creativeIds)) {
            creativeIds = new ConcurrentSkipListSet<>();
            unitCreativeMap.put(value.getUnitId(), creativeIds);
        }
        creativeIds.add(value.getCreativeId());
        log.info("after add: {}", objectMap);
    }

    @Override
    public void update(String key, CreativeUnitObject value) {
        log.error("CreativeUnit index can not support update");
    }

    @Override
    public void delete(String key, CreativeUnitObject value) {
        log.info("before delete: {}", objectMap);
        objectMap.remove(key);

        Set<Long> unitIds = creativeUnitMap.get(value.getCreativeId());
        if (CollectionUtils.isNotEmpty(unitIds)) {
            unitIds.remove(value.getUnitId());
        }

        Set<Long> creativeIds = unitCreativeMap.get(value.getUnitId());
        if (CollectionUtils.isNotEmpty(creativeIds)) {
            creativeIds.remove(value.getCreativeId());
        }

        log.info("after delete: {}", objectMap);
    }
}