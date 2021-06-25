package com.mall.goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.goods.entity.GoodsCategoryEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2021/6/23 10:42
 * @Version 1.0
 */
@Mapper
public interface GoodsCategoryMapper extends BaseMapper<GoodsCategoryEntity> {

    /**
     * 根据父id获取商品分类树
     * @return
     */
    List<GoodsCategoryEntity> queryGoodsCategoryTree(Long id);

    /**
     * 根据父id，查询子节点
     * @param parentId
     * @return
     */
    List<GoodsCategoryEntity> queryListParentId(Long parentId);
}
