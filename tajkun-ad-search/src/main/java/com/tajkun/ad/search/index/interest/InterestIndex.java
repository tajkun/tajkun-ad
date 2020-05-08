package com.tajkun.ad.search.index.interest;

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
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-04-24 20:46
 **/
@Slf4j
@Component
public class InterestIndex implements IndexAware<String, Set<Long>> {

    // 反向索引 interestTag -> unitIds
    private static Map<String, Set<Long>> interestUnitMap;
    // 正向索引 unitIds -> interestTags
    private static Map<Long, Set<String>> unitInterestMap;

    static {
        interestUnitMap = new ConcurrentHashMap<>();
        unitInterestMap = new ConcurrentHashMap<>();
    }

    @Override
    public Set<Long> get(String key) {
        return interestUnitMap.get(key);
    }

    @Override
    public void add(String key, Set<Long> value) {
        log.info("InterestIndex before add: {}", unitInterestMap);
        Set<Long> unitIds = CommonUtils.getorCreate(
                key,
                interestUnitMap,
                ConcurrentSkipListSet::new
        );
        unitIds.addAll(value);

        for (Long unitId : value) {
            Set<String> interestTags = CommonUtils.getorCreate(
                    unitId,
                    unitInterestMap,
                    ConcurrentSkipListSet::new
            );
            interestTags.add(key);
        }
        log.info("InterestIndex after add: {}", unitInterestMap);
    }

    @Override
    public void update(String key, Set<Long> value) {
        log.error("interest index can not support update");
    }

    @Override
    public void delete(String key, Set<Long> value) {
        log.info("InterestIndex before delete: {}", unitInterestMap);
        // 避免全部删除
        Set<Long> unitIds = CommonUtils.getorCreate(
                key,
                interestUnitMap,
                ConcurrentSkipListSet::new
        );
        unitIds.removeAll(value);

        for (Long unitId : value) {
            Set<String> interestTags = CommonUtils.getorCreate(
                    unitId,
                    unitInterestMap,
                    ConcurrentSkipListSet::new);
            interestTags.remove(key);
        }
        log.info("InterestIndex after delete: {}", unitInterestMap);
    }

    public boolean match(Long unitId, List<String> interestTags) {
        if (unitInterestMap.containsKey(unitId)
                && CollectionUtils.isNotEmpty(unitInterestMap.get(unitId))) {
            Set<String> interests = unitInterestMap.get(unitId);
            return CollectionUtils.isSubCollection(interestTags, interests);
        }
        return false;
    }
}