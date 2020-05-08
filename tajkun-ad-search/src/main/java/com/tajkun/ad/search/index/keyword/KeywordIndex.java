package com.tajkun.ad.search.index.keyword;

import com.tajkun.ad.search.index.IndexAware;
import com.tajkun.ad.search.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;

/**
 * @program: tajkun-ad
 * @description: 使用倒排索引
 * @author: Jiakun
 * @create: 2020-04-24 18:04
 **/
@Slf4j
@Component
public class KeywordIndex implements IndexAware<String, Set<Long>> {

    // 反向：关键词 -> 推广单元id
    private static Map<String, Set<Long>> keywordUnitMap;
    // 正向：推广单元id -> 关键词
    private static Map<Long, Set<String>> unitKeywordMap;

    static {
        keywordUnitMap = new ConcurrentHashMap<>();
        unitKeywordMap = new ConcurrentHashMap<>();
    }

    @Override
    public Set<Long> get(String key) {

        if (StringUtils.isEmpty(key)) {
            return Collections.emptySet();
        }

        Set<Long> result = keywordUnitMap.get(key);
        if (result == null) {
            return Collections.emptySet();
        }
        return result;
    }

    @Override
    public void add(String key, Set<Long> value) {
        log.info("KeywordIndex before add: {}", unitKeywordMap);
        // todo: keywordUnitMap
        Set<Long> unitIdSet = CommonUtils.getorCreate(
                key,
                keywordUnitMap,
                ConcurrentSkipListSet::new);
        unitIdSet.addAll(value);

        for (Long unitId : value) {
            Set<String> keywordSet = CommonUtils.getorCreate(
                    unitId,
                    unitKeywordMap,
                    ConcurrentSkipListSet::new);
            keywordSet.add(key);
        }
        log.info("KeywordIndex after add: {}", unitKeywordMap);
    }

    @Override
    public void update(String key, Set<Long> value) {
        // 更新操作涉及遍历两个map和set,成本太高,暂不支持更新（直接删除再添加）
        log.error("keyword index can not support update");
    }

    @Override
    public void delete(String key, Set<Long> value) {
        log.info("KeywordIndex before delete: {}", unitKeywordMap);
        Set<Long> unitIds = CommonUtils.getorCreate(
                key,
                keywordUnitMap,
                ConcurrentSkipListSet::new
        );
        unitIds.removeAll(value);

        for (Long unitId : value) {
            Set<String> keywordSet = CommonUtils.getorCreate(
                    unitId, unitKeywordMap,
                    ConcurrentSkipListSet::new
            );
            keywordSet.remove(key);
        }
        log.info("KeywordIndex after delete: {}", unitKeywordMap);
    }

    public boolean match(Long unitId, List<String> keywords) {
        if (unitKeywordMap.containsKey(unitId)
                && CollectionUtils.isNotEmpty(unitKeywordMap.get(unitId))) {

            Set<String> unitKeywords = unitKeywordMap.get(unitId);
            return CollectionUtils.isSubCollection(keywords, unitKeywords);
        }
        return false;
    }

}