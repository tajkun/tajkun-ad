package com.tajkun.ad.search.index.creative;

import com.tajkun.ad.search.index.IndexAware;
import com.tajkun.ad.search.index.unit.UnitObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-04-24 21:37
 **/
@Slf4j
@Component
public class CreativeIndex implements IndexAware<Long, CreativeObject> {

    // 存放索引对象  正向索引
    private static Map<Long, CreativeObject> objectMap;

    static {
        objectMap = new ConcurrentHashMap<>();
    }

    @Override
    public CreativeObject get(Long key) {
        return objectMap.get(key);
    }

    @Override
    public void add(Long key, CreativeObject value) {
        log.info("before add: {}", objectMap);
        objectMap.put(key, value);
        log.info("after add: {}", objectMap);
    }

    @Override
    public void update(Long key, CreativeObject value) {
        log.info("before update: {}", objectMap);
        CreativeObject oldObject = objectMap.get(key);
        if (null == oldObject) {
            objectMap.put(key, value);
        } else {
            oldObject.update(value);
        }
        log.info("after update: {}", objectMap);
    }

    @Override
    public void delete(Long key, CreativeObject value) {
        log.info("before delete: {}", objectMap);
        objectMap.remove(key);
        log.info("after delete: {}", objectMap);
    }

    // 根据id 获取对象
    public List<CreativeObject> fetch(Collection<Long> creativeIds) {
        if (CollectionUtils.isEmpty(creativeIds)) {
            return Collections.emptyList();
        }
        List<CreativeObject> result = new ArrayList<>();
        creativeIds.forEach(id -> {
            CreativeObject object = get(id);
            if (null == object) {
                log.error("creativeObject not found: {}", id);
                return;
            }
            result.add(object);
        });
        return result;
    }

}