package com.mall.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.mall.admin.entity.RegionEntity;
import com.mall.admin.mapper.RegionMapper;
import com.mall.admin.service.RegionService;
import com.mall.common.base.utils.MapUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2021/8/15 9:53
 * @Version 1.0
 */
@Service("regionService")
public class RegionServiceImpl extends ServiceImpl<RegionMapper, RegionEntity>
        implements RegionService {

    @Override
    public List<RegionEntity> queryRegionList(Map<String, Object> paramsMap) {
        return this.listByMap(paramsMap);
    }

    @Override
    public RegionEntity getRegionById(Long id) {
        return this.getById(id);
    }

    /**
     * 获取地区信息，包括省市区县
     * @param regions
     * @return
     */
    @Override
    public String getRegionsNameByRegions(String regions) {
        String[] regionId = regions.split(",");
        QueryWrapper<RegionEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", Lists.newArrayList(
                Long.valueOf(regionId[0]),
                Long.valueOf(regionId[1]),
                Long.valueOf(regionId[2])));
        List<RegionEntity> regionEntityList = this.list(queryWrapper);
        return regionEntityList.get(0).getName() + " " + regionEntityList.get(1).getName() + " " + regionEntityList.get(2).getName();
    }

    /**
     * 新增地区
     * @param regionEntity
     * @return
     */
    @Override
    public int saveRegion(@RequestBody RegionEntity regionEntity) {
        return baseMapper.insert(regionEntity);
    }

    /**
     * 修改地区
     * @param regionEntity
     * @return
     */
    @Override
    public int updateRegion(@RequestBody RegionEntity regionEntity) {
        return baseMapper.updateById(regionEntity);
    }

    /**
     * 删除地区（有下级区域需要递归删除）
     * @param id
     * @return
     */
    @Override
    @Transactional
    public int deleteRegion(Long id) {
        //所有需要删除的节点（地区）id
        List<Long> deleteIds = new ArrayList<>();
        deleteRegionByRecursion(id, deleteIds);
        //删除子级
        if(deleteIds.size()>0){
            baseMapper.deleteBatchIds(deleteIds);
        }
        //删除自己
        return baseMapper.deleteById(id);
    }

    /**
     * 递归删除
     */
    private void deleteRegionByRecursion(Long id, List<Long> deleteIds){
        List<RegionEntity> subRegionList = this.listByMap(new MapUtil().put("parent_id", id));
        if (subRegionList != null && subRegionList.size() > 0){
            for (int i=0; i<subRegionList.size(); i++){
                deleteIds.add(subRegionList.get(i).getId());
                deleteRegionByRecursion(subRegionList.get(i).getId(),deleteIds);
            }
        }
    }
}
