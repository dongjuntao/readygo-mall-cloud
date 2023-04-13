package com.mall.goods.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.goods.entity.GoodsEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author DongJunTao
 * @Description 商品服务mapper 门户端使用
 * @Date 2023/4/13 13:24
 * @Version 1.0
 */
@Mapper
public interface FrontGoodsMapper extends BaseMapper<GoodsEntity> {

    IPage<GoodsEntity> queryPage(
            @Param("page") Page<GoodsEntity> page,
            @Param(Constants.WRAPPER) QueryWrapper<GoodsEntity> wrapper,
            @Param("adminUserId") Long adminUserId);

    GoodsEntity getGoodsAndSku(Long id);

    /**
     * 根据商品id集合，查询商品【包括商品sku信息】
     * @param goodsIds
     * @return
     */
    List<GoodsEntity> getByGoodsIds(@Param("goodsIds") Long[] goodsIds);
}
