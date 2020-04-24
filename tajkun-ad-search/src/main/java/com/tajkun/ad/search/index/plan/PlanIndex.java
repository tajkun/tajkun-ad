package com.tajkun.ad.search.index.plan;

import com.tajkun.ad.search.index.IndexAware;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: tajkun-ad
 * @description: 索引操作实现
 * @author: Jiakun
 * @create: 2020-04-24 17:16
 **/
@Slf4j
@Component
public class PlanIndex implements IndexAware<Long, PlanObject> {

    // 存放索引对象
    private static Map<Long, PlanObject> objectMap;

    static {
        // 线程安全
        objectMap = new ConcurrentHashMap<>();
    }

    @Override
    public PlanObject get(Long key) {
        return objectMap.get(key);
    }

    @Override
    public void add(Long key, PlanObject value) {
        log.info("before add: {}", objectMap);
        objectMap.put(key, value);
        log.info("after add: {}", objectMap);
    }

    @Override
    public void update(Long key, PlanObject value) {
        log.info("before update: {}", objectMap);
        PlanObject oldObject = objectMap.get(key);
        if (null == oldObject) {
            objectMap.put(key, value);
        } else {
            oldObject.update(value);
        }
        log.info("after update: {}", objectMap);
    }

    @Override
    public void delete(Long key, PlanObject value) {
        log.info("before delete: {}", objectMap);
        objectMap.remove(key);
        log.info("after delete: {}", objectMap);
    }
}