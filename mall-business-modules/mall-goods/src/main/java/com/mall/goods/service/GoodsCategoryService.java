package com.mall.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.goods.entity.GoodsCategoryEntity;

import java.util.List;
import java.util.Map;


/**
 * @Author DongJunTao
 * @Description 商品分类
 * @Date 2021/5/28 13:33
 * @Version 1.0
 */
public interface GoodsCategoryService extends IService<GoodsCategoryEntity> {

    /**
     * 根据父id，查询商品分类树
     * @param id
     * @return
     */
    Map<String,List<GoodsCategoryEntity>> queryMergeGoodsCategoryTree(Long id);

    List<GoodsCategoryEntity> queryGoodsCategoryTree(Long id);

    /**
     * 根据父id，查询子节点
     * @param parentId
     * @return
     */
    List<GoodsCategoryEntity> queryListParentId(Long parentId);

}
