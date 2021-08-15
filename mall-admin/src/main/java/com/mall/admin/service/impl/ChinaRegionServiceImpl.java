package com.mall.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.admin.entity.ChinaRegionEntity;
import com.mall.admin.mapper.ChinaRegionMapper;
import com.mall.admin.service.ChinaRegionService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2021/8/15 9:53
 * @Version 1.0
 */
@Service("chinaRegionService")
public class ChinaRegionServiceImpl extends ServiceImpl<ChinaRegionMapper, ChinaRegionEntity>
        implements ChinaRegionService {

    @Override
    public List<ChinaRegionEntity> queryChinaRegionList(Map<String, Object> paramsMap) {
        return this.listByMap(paramsMap);
    }
}
