package com.mall.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.common.base.utils.MapUtil;
import com.mall.common.base.utils.PageBuilder;
import com.mall.common.base.utils.PageUtil;
import com.mall.goods.entity.GoodsEntity;
import com.mall.goods.mapper.GoodsMapper;
import com.mall.goods.service.GoodsService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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
    public PageUtil queryPage(Integer pageNum, Integer pageSize, String name, Long adminUserId, String categoryIds) {
        Map<String,Object> pageParams = new MapUtil().put("pageNum",pageNum).put("pageSize",pageSize);
        Page<GoodsEntity> page = (Page<GoodsEntity>)new PageBuilder<GoodsEntity>().getPage(pageParams);
        QueryWrapper<GoodsEntity> wrapper = new QueryWrapper<>();
        wrapper
                .like(StringUtils.isNotBlank(name), "g.name", name)
                .eq(adminUserId != null, "admin_user_id", adminUserId)
                .orderByDesc("create_time");
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
    public List<GoodsEntity> getAllGoodsList(Long adminUserId) {
        QueryWrapper<GoodsEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(adminUserId != null, "admin_user_id", adminUserId)
                .orderByDesc("create_time");;
        return baseMapper.selectList(queryWrapper);
    }

    /**
     * 根据id集合，查询所有商品信息
     * @param ids
     * @return
     */
    @Override
    public List<GoodsEntity> getByIds(Long[] ids) {
        return baseMapper.selectBatchIds(Arrays.asList(ids));
    }

    @Override
    public List<GoodsEntity> getAllGoodsWithDetail() {
        return baseMapper.getAllGoodsWithDetail();
    }
}
