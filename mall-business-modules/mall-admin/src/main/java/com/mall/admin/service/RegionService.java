package com.mall.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.admin.entity.RegionEntity;

import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 中国区域
 * @Date 2021/8/15 9:52
 * @Version 1.0
 */
public interface RegionService extends IService<RegionEntity> {

    List<RegionEntity> queryRegionList(Map<String, Object> paramsMap);

    RegionEntity getRegionById(Long id);

    String getRegionsNameByRegions(String regions);

    int saveRegion(RegionEntity regionEntity);

    int updateRegion(RegionEntity regionEntity);

    int deleteRegion(Long id);

}
