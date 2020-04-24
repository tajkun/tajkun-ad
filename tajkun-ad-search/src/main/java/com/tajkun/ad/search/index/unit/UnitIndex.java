package com.tajkun.ad.search.index.unit;

import com.tajkun.ad.search.index.IndexAware;
import com.tajkun.ad.search.index.plan.PlanObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
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

    // 存放索引对象
    private static Map<Long, UnitObject> objectMap;

    static {
        // 线程安全
        objectMap = new ConcurrentHashMap<>();
    }

    @Override
    public UnitObject get(Long key) {
        return objectMap.get(key);
    }

    @Override
    public void add(Long key, UnitObject value) {
        log.info("before add: {}", objectMap);
        objectMap.put(key, value);
        log.info("after add: {}", objectMap);
    }

    @Override
    public void update(Long key, UnitObject value) {
        log.info("before update: {}", objectMap);
        UnitObject oldObject = objectMap.get(key);
        if (null == oldObject) {
            objectMap.put(key, value);
        } else {
            oldObject.update(value);
        }
        log.info("after update: {}", objectMap);
    }

    @Override
    public void delete(Long key, UnitObject value) {
        log.info("before delete: {}", objectMap);
        objectMap.remove(key);
        log.info("after delete: {}", objectMap);
    }
}