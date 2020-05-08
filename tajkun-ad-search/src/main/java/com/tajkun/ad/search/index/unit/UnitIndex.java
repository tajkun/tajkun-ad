package com.tajkun.ad.search.index.unit;

import com.tajkun.ad.search.index.IndexAware;
import com.tajkun.ad.search.index.plan.PlanObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-04-24 17:51
 **/
@Slf4j
@Component
public class UnitIndex implements IndexAware<Long, UnitObject> {

    // 存放索引对象  正向索引
    private static Map<Long, UnitObject> objectMap;

    static {
        // 线程安全
        objectMap = new ConcurrentHashMap<>();
    }

    // 查看推广单元中是否有positionType匹配的
    public Set<Long> match(Integer positionType) {
        Set<Long> adUnitIds = new HashSet<>();
        objectMap.forEach((k, v) -> {
            if (UnitObject.isAdSlotTypeOK(positionType, v.getPositionType())) {
                adUnitIds.add(k);
            }
        });
        return adUnitIds;
    }

    public List<UnitObject> fetch(Collection<Long> adUnitIds) {
        if (CollectionUtils.isEmpty(adUnitIds)) {
            return Collections.emptyList();
        }
        List<UnitObject> result = new ArrayList<>();
        adUnitIds.forEach(u -> {
            UnitObject object = get(u);
            if (object == null) {
                log.error("UnitObject not found: {}", u);
                return;
            }
            result.add(object);
        });
        return result;
    }

    @Override
    public UnitObject get(Long key) {
        return objectMap.get(key);
    }

    @Override
    public void add(Long key, UnitObject value) {
        log.info("UnitIndex before add: {}", objectMap);
        objectMap.put(key, value);
        log.info("UnitIndex after add: {}", objectMap);
    }

    @Override
    public void update(Long key, UnitObject value) {
        log.info("UnitIndex before update: {}", objectMap);
        UnitObject oldObject = objectMap.get(key);
        if (null == oldObject) {
            objectMap.put(key, value);
        } else {
            oldObject.update(value);
        }
        log.info("UnitIndex after update: {}", objectMap);
    }

    @Override
    public void delete(Long key, UnitObject value) {
        log.info("UnitIndex before delete: {}", objectMap);
        objectMap.remove(key);
        log.info("UnitIndex after delete: {}", objectMap);
    }

}