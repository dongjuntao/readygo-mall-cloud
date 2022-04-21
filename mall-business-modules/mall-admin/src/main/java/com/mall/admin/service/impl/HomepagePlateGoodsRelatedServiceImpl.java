package com.mall.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.admin.entity.HomepagePlateGoodsRelatedEntity;
import com.mall.admin.mapper.HomepagePlateGoodsRelatedMapper;
import com.mall.admin.service.HomepagePlateGoodsRelatedService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author DongJunTao
 * @Description 板块-商品关联表板块-商品关联Service实现
 * @Date 2022/3/19 16:58
 * @Version 1.0
 */
@Service("homepagePlateGoodsRelatedService")
public class HomepagePlateGoodsRelatedServiceImpl extends ServiceImpl<HomepagePlateGoodsRelatedMapper, HomepagePlateGoodsRelatedEntity>
        implements HomepagePlateGoodsRelatedService {

    /**
     * 根据板块id查询所有关联商品
     * @param plateId
     * @return
     */
    @Override
    public List<HomepagePlateGoodsRelatedEntity> getHomepagePlateGoodsRelatedList(Long plateId) {
        QueryWrapper<HomepagePlateGoodsRelatedEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("plate_id", plateId);
        return this.list(queryWrapper);
    }

    /**
     * 根据板块id批量删除关联商品信息
     * @param plateId
     */
    @Override
    public void deleteBatch(Long plateId) {
        QueryWrapper<HomepagePlateGoodsRelatedEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("plate_id", plateId);
        this.remove(queryWrapper);
    }

    /**
     * 保存商板块-品关联信息
     * @param plateId
     * @param goodsIds
     * @return
     */
    @Override
    public boolean save(Long plateId, Long[] goodsIds) {
        List<HomepagePlateGoodsRelatedEntity> homepagePlateGoodsRelatedEntityList = new ArrayList<>();
        for (Long goodsId : goodsIds) {
            HomepagePlateGoodsRelatedEntity homepagePlateGoodsRelatedEntity = new HomepagePlateGoodsRelatedEntity();
            homepagePlateGoodsRelatedEntity.setPlateId(plateId);
            homepagePlateGoodsRelatedEntity.setGoodsId(goodsId);
            homepagePlateGoodsRelatedEntityList.add(homepagePlateGoodsRelatedEntity);
        }
        return this.saveBatch(homepagePlateGoodsRelatedEntityList);
    }
}
