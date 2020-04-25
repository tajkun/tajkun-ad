package com.tajkun.ad.delivery;

import com.alibaba.fastjson.JSON;
import com.tajkun.ad.common.export.ExportConstant;
import com.tajkun.ad.common.export.table.*;
import com.tajkun.ad.delivery.constant.CommonStatus;
import com.tajkun.ad.delivery.pojo.Creative;
import com.tajkun.ad.delivery.pojo.PromotionPlan;
import com.tajkun.ad.delivery.pojo.PromotionUnit;
import com.tajkun.ad.delivery.pojo.unit_dimension.CreativeUnit;
import com.tajkun.ad.delivery.pojo.unit_dimension.UnitDistrict;
import com.tajkun.ad.delivery.pojo.unit_dimension.UnitInterest;
import com.tajkun.ad.delivery.pojo.unit_dimension.UnitKeyword;
import com.tajkun.ad.delivery.repository.CreativeRepository;
import com.tajkun.ad.delivery.repository.PromotionPlanRepository;
import com.tajkun.ad.delivery.repository.PromotionUnitRepository;
import com.tajkun.ad.delivery.repository.unit_dimension.CreativeUnitRepository;
import com.tajkun.ad.delivery.repository.unit_dimension.UnitDistrictRepository;
import com.tajkun.ad.delivery.repository.unit_dimension.UnitInterestRepository;
import com.tajkun.ad.delivery.repository.unit_dimension.UnitKeywordRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: tajkun-ad
 * @description: 将索引对象导入文件 todo: 拆分为独立微服务，导入delivery 方法也需要重构
 * @author: Jiakun
 * @create: 2020-04-25 09:36
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ExportDataService {

    @Autowired
    private PromotionPlanRepository planRepository;
    @Autowired
    private PromotionUnitRepository unitRepository;
    @Autowired
    private CreativeRepository creativeRepository;
    @Autowired
    private CreativeUnitRepository creativeUnitRepository;
    @Autowired
    private UnitKeywordRepository unitKeywordRepository;
    @Autowired
    private UnitInterestRepository unitInterestRepository;
    @Autowired
    private UnitDistrictRepository unitDistrictRepository;

    @Test
    public void exportTableData() {
        log.error("开始---------");
        exportPlanTable(String.format("%s%s", ExportConstant.DATA_ROOT_DIR, ExportConstant.PROMOTION_PLAN));
        exportUnitTable(String.format("%s%s", ExportConstant.DATA_ROOT_DIR, ExportConstant.PROMOTION_UNIT));
        exportCreativeTable(String.format("%s%s", ExportConstant.DATA_ROOT_DIR, ExportConstant.CREATIVE));
        exportCreativeUnitTable(String.format("%s%s", ExportConstant.DATA_ROOT_DIR, ExportConstant.CREATIVE_UNIT));
        exportUnitKeywordTable(String.format("%s%s", ExportConstant.DATA_ROOT_DIR, ExportConstant.UNIT_KEYWORD));
        exportUnitInterestTable(String.format("%s%s", ExportConstant.DATA_ROOT_DIR, ExportConstant.UNIT_INTEREST));
        exportUnitDistrictTable(String.format("%s%s", ExportConstant.DATA_ROOT_DIR, ExportConstant.UNIT_DISTRICT));
        log.error("结束--------");
    }

    public void exportPlanTable(String fileName) {


        List<PromotionPlan> plans = planRepository.findAllByPlanStatus(CommonStatus.VALID.getStatusCode());
        if (CollectionUtils.isEmpty(plans)) {
            return;
        }

        List<PlanTable> planTables = new ArrayList<>();
        plans.forEach(p -> planTables.add(
                new PlanTable(p.getId(), p.getUserId(), p.getPlanStatus(), p.getStartDate(), p.getEndDate())
        ));
        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (PlanTable planTable : planTables) {
                System.out.println("-----------" + planTable);
                writer.write(JSON.toJSONString(planTable));
                writer.newLine();
            }
            log.error("exportPlan 执行结束");
        } catch (IOException e) {
            log.error("exportPlanTable error: {}", e.getMessage());
        }
    }

    public void exportUnitTable(String fileName) {

        List<PromotionUnit> units = unitRepository.findAllByUnitStatus(CommonStatus.VALID.getStatusCode());
        if (CollectionUtils.isEmpty(units)) {
            return;
        }

        List<UnitTable> unitTables = new ArrayList<>();
        units.forEach(u -> unitTables.add(
                new UnitTable(u.getId(), u.getUnitStatus(), u.getPositionType(), u.getPlanId())
        ));
        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (UnitTable unitTable : unitTables) {
                writer.write(JSON.toJSONString(unitTable));
                writer.newLine();
            }
          // writer.close();  已经不需要了
        } catch (IOException e) {
            log.error("exportUnitTable error: {}", e.getMessage());
        }
    }

    public void exportCreativeTable(String fileName) {

        List<Creative> creatives = creativeRepository.findAll();
        if (CollectionUtils.isEmpty(creatives)) {
            return;
        }

        List<CreativeTable> creativeTables = new ArrayList<>();
        creatives.forEach(c -> creativeTables.add(
                new CreativeTable(c.getId(), c.getName(), c.getType(), c.getMaterialType(),
                        c.getHeight(), c.getWidth(), c.getAuditStatus(), c.getUrl())
        ));
        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (CreativeTable creativeTable : creativeTables) {
                writer.write(JSON.toJSONString(creativeTable));
                writer.newLine();
            }
        } catch (IOException e) {
            log.error("exportCreativeTable error: {}", e.getMessage());
        }
    }

    public void exportCreativeUnitTable(String fileName) {

        List<CreativeUnit> creativeUnits = creativeUnitRepository.findAll();
        if (CollectionUtils.isEmpty(creativeUnits)) {
            return;
        }

        List<CreativeUnitTable> creativeUnitTables = new ArrayList<>();
        creativeUnits.forEach(cu -> creativeUnitTables.add(
                new CreativeUnitTable(cu.getCreativeId(), cu.getUnitId() )
        ));
        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (CreativeUnitTable creativeUnitTable : creativeUnitTables) {
                writer.write(JSON.toJSONString(creativeUnitTable));
                writer.newLine();
            }
            // writer.close();  已经不需要了
        } catch (IOException e) {
            log.error("exportCreativeUnitTable error: {}", e.getMessage());
        }
    }

    public void exportUnitDistrictTable(String fileName) {

        List<UnitDistrict> unitDistricts = unitDistrictRepository.findAll();
        if (CollectionUtils.isEmpty(unitDistricts)) {
            return;
        }

        List<UnitDistrictTable> unitDistrictTables = new ArrayList<>();
        unitDistricts.forEach(ud -> unitDistrictTables.add(
                new UnitDistrictTable(ud.getUnitId(), ud.getProvince(), ud.getCity())
        ));
        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (UnitDistrictTable unitDistrictTable : unitDistrictTables) {
                writer.write(JSON.toJSONString(unitDistrictTable));
                writer.newLine();
            }
            // writer.close();  已经不需要了
        } catch (IOException e) {
            log.error("exportUnitDistrictTable error: {}", e.getMessage());
        }
    }

    public void exportUnitInterestTable(String fileName) {

        List<UnitInterest> unitInterests = unitInterestRepository.findAll();
        if (CollectionUtils.isEmpty(unitInterests)) {
            return;
        }

        List<UnitInterestTable> unitInterestTables = new ArrayList<>();
        unitInterests.forEach(ui -> unitInterestTables.add(
                new UnitInterestTable(ui.getUnitId(), ui.getInterestTag())
        ));
        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (UnitInterestTable unitInterestTable : unitInterestTables) {
                writer.write(JSON.toJSONString(unitInterestTable));
                writer.newLine();
            }
            // writer.close();  已经不需要了
        } catch (IOException e) {
            log.error("exportUnitInterestTable error: {}", e.getMessage());
        }
    }

    public void exportUnitKeywordTable(String fileName) {

        List<UnitKeyword> unitKeywords = unitKeywordRepository.findAll();
        if (CollectionUtils.isEmpty(unitKeywords)) {
            return;
        }

        List<UnitKeywordTable> unitKeywordTables = new ArrayList<>();
        unitKeywords.forEach(uk -> unitKeywordTables.add(
                new UnitKeywordTable(uk.getUnitId(), uk.getKeyword())
        ));
        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (UnitKeywordTable unitKeywordTable : unitKeywordTables) {
                writer.write(JSON.toJSONString(unitKeywordTable));
                writer.newLine();
            }
            // writer.close();  已经不需要了
        } catch (IOException e) {
            log.error("exportUnitKeywordTable error: {}", e.getMessage());
        }
    }

}