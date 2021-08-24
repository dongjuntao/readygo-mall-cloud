package com.mall.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.admin.entity.ChinaRegionEntity;

import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 中国区域
 * @Date 2021/8/15 9:52
 * @Version 1.0
 */
public interface ChinaRegionService extends IService<ChinaRegionEntity> {

    List<ChinaRegionEntity> queryChinaRegionList(Map<String, Object> paramsMap);

    ChinaRegionEntity getChinaRegionById(Long id);

    String getRegionsNameByRegions(String regions);

}
