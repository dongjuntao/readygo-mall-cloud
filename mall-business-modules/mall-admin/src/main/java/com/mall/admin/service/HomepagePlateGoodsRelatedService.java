package com.mall.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.admin.entity.HomepagePlateGoodsRelatedEntity;

import java.util.List;

/**
 * @Author DongJunTao
 * @Description 板块-商品关联表板块-商品关联Service
 * @Date 2022/3/19 16:55
 * @Version 1.0
 */
public interface HomepagePlateGoodsRelatedService extends IService<HomepagePlateGoodsRelatedEntity> {

    /**
     * 根据板块id查询所有关联商品
     * @param plateId
     * @return
     */
    List<HomepagePlateGoodsRelatedEntity> getHomepagePlateGoodsRelatedList(Long plateId);

    /**
     * 根据板块id批量删除关联商品信息
     * @param plateId
     */
    void deleteBatch(Long plateId);

    /**
     * 保存商板块-品关联信息
     * @param plateId
     * @param goodsIds
     * @return
     */
    boolean save(Long plateId, Long[] goodsIds);
}
