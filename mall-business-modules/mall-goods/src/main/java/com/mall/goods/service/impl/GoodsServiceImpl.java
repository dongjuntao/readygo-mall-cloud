package com.mall.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.common.base.utils.PageBuilder;
import com.mall.common.base.utils.PageUtil;
import com.mall.goods.entity.GoodsEntity;
import com.mall.goods.mapper.GoodsMapper;
import com.mall.goods.service.GoodsService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2021/6/30 16:54
 * @Version 1.0
 */
@Service("goodsService")
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, GoodsEntity> implements GoodsService {

    @Override
    public PageUtil queryPage(Map<String, Object> params) {
        Page<GoodsEntity> page = (Page<GoodsEntity>)new PageBuilder<GoodsEntity>().getPage(params);
        QueryWrapper<GoodsEntity> wrapper = new QueryWrapper<>();
        String name = params.get("name") == null ? null : String.valueOf(params.get("name"));//商品名称
        Long adminUserId = params.get("adminUserId") == null ? null: Long.valueOf((params.get("adminUserId").toString()));
        wrapper
                .like(StringUtils.isNotBlank(name), "name", name)
                .eq(adminUserId != null, "admin_user_id", adminUserId)
                .orderByDesc("create_time");
        String categoryIds = params.get("categoryIds") == null ? null : String.valueOf(params.get("categoryIds"));//商品分类id集合
        if (StringUtils.isNotBlank(categoryIds)) {
            String[] categorySplit = categoryIds.split(",");
            if (categorySplit.length == 1) { //按一级分类窜查询
                wrapper.eq("goods_category_id_first", categorySplit[0]);
            } else if (categorySplit.length == 2) { //按二级分类窜查询
                wrapper.eq("goods_category_id_second", categorySplit[1]);
            } else if (categorySplit.length == 3) { //按三级分类窜查询
                wrapper.eq("goods_category_id_third", categorySplit[2]);
            }
        }
        IPage<GoodsEntity> iPage = baseMapper.queryPage(page, wrapper, adminUserId);
        return new PageUtil(iPage);
    }

    @Override
    public GoodsEntity getGoodsAndSku(Long id) {
        return baseMapper.getGoodsAndSku(id);
    }

    /**
     * 上架 / 下架
     * @param onSale
     */
    @Override
    public int updateOnSale(Long goodsId, Boolean onSale) {
        GoodsEntity goodsEntity = baseMapper.selectById(goodsId);
        goodsEntity.setOnSale(onSale);
        return baseMapper.updateById(goodsEntity);
    }

    @Override
    public List<GoodsEntity> getAllGoodsList(Map<String, Object> params) {
        Long adminUserId = params.get("adminUserId") == null ? null: Long.valueOf((params.get("adminUserId").toString()));
        QueryWrapper<GoodsEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(adminUserId != null, "admin_user_id", adminUserId)
                .orderByDesc("create_time");;
        return baseMapper.selectList(queryWrapper);
    }

    /**
     * 根据商品id集合，查询商品【包括商品sku信息】
     * @param goodsIds
     * @return
     */
    @Override
    public List<GoodsEntity> getByGoodsIds(Long[] goodsIds) {
        return baseMapper.getByGoodsIds(goodsIds);
    }
}
