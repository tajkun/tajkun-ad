package com.tajkun.ad.search.retrieve.impl;

import com.alibaba.fastjson.JSON;
import com.tajkun.ad.search.index.CommonStatus;
import com.tajkun.ad.search.index.DataTable;
import com.tajkun.ad.search.index.creative.CreativeIndex;
import com.tajkun.ad.search.index.creative.CreativeObject;
import com.tajkun.ad.search.index.creativeunit.CreativeUnitIndex;
import com.tajkun.ad.search.index.district.DistrictIndex;
import com.tajkun.ad.search.index.interest.InterestIndex;
import com.tajkun.ad.search.index.keyword.KeywordIndex;
import com.tajkun.ad.search.index.unit.UnitIndex;
import com.tajkun.ad.search.index.unit.UnitObject;
import com.tajkun.ad.search.retrieve.ISearch;
import com.tajkun.ad.search.retrieve.vo.SearchRequest;
import com.tajkun.ad.search.retrieve.vo.SearchResponse;
import com.tajkun.ad.search.retrieve.vo.feature.DistrictFeature;
import com.tajkun.ad.search.retrieve.vo.feature.FeatureRelation;
import com.tajkun.ad.search.retrieve.vo.feature.InterestFeature;
import com.tajkun.ad.search.retrieve.vo.feature.KeywordFeature;
import com.tajkun.ad.search.retrieve.vo.media.AdSlot;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @program: tajkun-ad
 * @description: 根据request进行推广单元的过滤，由推广单元获得创意来构建response
 * @author: Jiakun
 * @create: 2020-05-02 11:33
 **/
@Slf4j
@Service
public class SearchImpl implements ISearch {

    @Override
    public SearchResponse fetchAds(SearchRequest request) {

        // 请求的广告位信息
        List<AdSlot> adSlots = request.getRequestInfo().getAdSlots();
        // 获取features
        KeywordFeature keywordFeature = request.getFeatureInfo().getKeywordFeature();
        DistrictFeature districtFeature = request.getFeatureInfo().getDistrictFeature();
        InterestFeature interestFeature = request.getFeatureInfo().getInterestFeature();
        // 获取关联关系
        FeatureRelation featureRelation = request.getFeatureInfo().getFeatureRelation();
        // 构造响应对象
        SearchResponse response = new SearchResponse();
        Map<String, List<SearchResponse.Creative>> adSlot2Ads = response.getAdSlot2Ads();

        for (AdSlot adSlot : adSlots) {
            Set<Long> targetUnitIdSet;
            // 根据流量类型获取初始的Unit  预筛选
            Set<Long> adUnitIdSet = DataTable.of(UnitIndex.class).match(adSlot.getPositionType());
            // 关联关系处理 进行单元维度再过滤
            if (featureRelation == FeatureRelation.AND) {
                filterKeywordFeature(adUnitIdSet, keywordFeature);
                filterDistrictFeature(adUnitIdSet, districtFeature);
                filterInterestFeature(adUnitIdSet, interestFeature);
                targetUnitIdSet = adUnitIdSet;

            } else {
                targetUnitIdSet = getORRelationUnitIds(adUnitIdSet, keywordFeature, districtFeature, interestFeature);
            }
            // 获取unitObjects
            List<UnitObject> unitObjects = DataTable.of(UnitIndex.class).fetch(targetUnitIdSet);
            // 过滤掉失效的
            filterUnitAndPlanStatus(unitObjects, CommonStatus.VALID);
            // 根据unitObject获取创意id
            List<Long> adIds = DataTable.of(CreativeUnitIndex.class).selectAds(unitObjects);
            // 根据创意id获取创意对象
            List<CreativeObject> creativeObjects = DataTable.of(CreativeIndex.class).fetch(adIds);
            // 根据AdSlot对创意过滤
            filterCreativeByAdSlot(creativeObjects, adSlot.getWidth(), adSlot.getHeight(), adSlot.getType());
            // 填充adSlot2Ads
            adSlot2Ads.put(adSlot.getAdSlotCode(), buildCreativeResponse(creativeObjects));
        }
        log.info("fetchAds: {}-{}", JSON.toJSONString(request), JSON.toJSONString(response));
        return response;
    }

    private void filterKeywordFeature(Collection<Long> adUnitIds, KeywordFeature keywordFeature) {
        if (CollectionUtils.isEmpty(adUnitIds)) {
            return;
        }
        // 关键词过滤
        if (CollectionUtils.isNotEmpty(keywordFeature.getKeywords())) {
            CollectionUtils.filter(adUnitIds, adUnitId ->
                DataTable.of(KeywordIndex.class).match(adUnitId, keywordFeature.getKeywords())
            );
        }
    }

    private void filterDistrictFeature(Collection<Long> adUnitIds, DistrictFeature districtFeature) {
        if (CollectionUtils.isEmpty(adUnitIds)) {
            return;
        }
        // 地域过滤
        if (CollectionUtils.isNotEmpty(districtFeature.getDistricts())) {
            CollectionUtils.filter(adUnitIds, adUnitId ->
                    DataTable.of(DistrictIndex.class).match(adUnitId, districtFeature.getDistricts())
            );
        }
    }

    private void filterInterestFeature(Collection<Long> adUnitIds, InterestFeature interestFeature) {
        if (CollectionUtils.isEmpty(adUnitIds)) {
            return;
        }
        // 兴趣过滤
        if (CollectionUtils.isNotEmpty(interestFeature.getInterests())) {
            CollectionUtils.filter(adUnitIds, adUnitId ->
                    DataTable.of(InterestIndex.class).match(adUnitId, interestFeature.getInterests())
            );
        }
    }

    // 并集关系单元维度过滤
    private Set<Long> getORRelationUnitIds(Set<Long> adUnitIdSet,
                                           KeywordFeature keywordFeature,
                                           DistrictFeature districtFeature,
                                           InterestFeature interestFeature) {
        if (CollectionUtils.isEmpty(adUnitIdSet)) {
            return Collections.emptySet();
        }
        Set<Long> keywordUnitIdSet = new HashSet<>(adUnitIdSet);
        Set<Long> districtUnitIdSet = new HashSet<>(adUnitIdSet);
        Set<Long> interestUnitIdSet = new HashSet<>(adUnitIdSet);

        filterKeywordFeature(keywordUnitIdSet, keywordFeature);
        filterDistrictFeature(districtUnitIdSet, districtFeature);
        filterInterestFeature(interestUnitIdSet, interestFeature);

        return new HashSet<>(CollectionUtils.union(
                CollectionUtils.union(keywordUnitIdSet, districtUnitIdSet),
                interestUnitIdSet
        ));
    }

    private void filterUnitAndPlanStatus(List<UnitObject> unitObjects, CommonStatus status) {
        if (CollectionUtils.isEmpty(unitObjects)) {
            return;
        }
        CollectionUtils.filter(unitObjects,
                unitObject -> unitObject.getUnitStatus().equals(status.getStatus())
        && unitObject.getPlanObject().getPlanStatus().equals(status.getStatus()));
    }

    // 根据slot过滤创意
    private void filterCreativeByAdSlot(List<CreativeObject> creativeObjects,
                                        Integer width, Integer height, List<Integer> type) {
        if (CollectionUtils.isEmpty(creativeObjects)) {
            return;
        }
        CollectionUtils.filter(creativeObjects,
                object -> object.getAuditStatus().equals(CommonStatus.VALID.getStatus())
        && object.getWidth().equals(width) && object.getHeight().equals(height)
        && type.contains(object.getType()));
    }

    // 指定一个广告位只返回一个创意（也可多个）
    private List<SearchResponse.Creative> buildCreativeResponse(List<CreativeObject> creativeObjects) {
        if (CollectionUtils.isEmpty(creativeObjects)) {
            return Collections.emptyList();
        }
        // 随机获取creativeObjects中的一个
        CreativeObject randomObject = creativeObjects.get(
                Math.abs(new Random().nextInt()) % creativeObjects.size());
        // 这个方法主要用于只有一个元素的优化，减少内存分配，无需分配额外的内存，
        // 可以从SingletonList内部类看得出来,由于只有一个element,因此可以做到内存分配最小化，
        // 相比之下ArrayList的DEFAULT_CAPACITY=10个。
        return Collections.singletonList(SearchResponse.convert(randomObject));
    }

}