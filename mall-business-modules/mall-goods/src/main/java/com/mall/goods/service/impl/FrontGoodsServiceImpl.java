package com.mall.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.common.base.utils.MapUtil;
import com.mall.common.base.utils.PageBuilder;
import com.mall.common.base.utils.PageUtil;
import com.mall.goods.entity.GoodsEntity;
import com.mall.goods.mapper.FrontGoodsMapper;
import com.mall.goods.service.FrontGoodsService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 商品服务 门户端使用
 * @Date 2023/4/13 13:23
 * @Version 1.0
 */
@Service("frontGoodsService")
public class FrontGoodsServiceImpl extends ServiceImpl<FrontGoodsMapper, GoodsEntity> implements FrontGoodsService {
    @Override
    public GoodsEntity getGoodsAndSku(Long id) {
        return baseMapper.getGoodsAndSku(id);
    }

    /**
     *
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @param name 商品名称
     * @param adminUserId 商家id
     * @param categoryIds 商品分类id集
     * @return
     */
    @Override
    public PageUtil queryPage(Integer pageNum, Integer pageSize, String name, Long adminUserId, String categoryIds) {
        Map<String,Object> pageParams = new MapUtil().put("pageNum",pageNum).put("pageSize",pageSize);
        Page<GoodsEntity> page = (Page<GoodsEntity>)new PageBuilder<GoodsEntity>().getPage(pageParams);
        QueryWrapper<GoodsEntity> wrapper = new QueryWrapper<>();
        wrapper
                .like(StringUtils.isNotBlank(name), "g.name", name)
                .eq(adminUserId != null, "admin_user_id", adminUserId)
                .eq("goods_status", "'ON_SALE'")
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
    public List<GoodsEntity> getByGoodsIds(Long[] goodsIds) {
        return baseMapper.getByGoodsIds(goodsIds);
    }
}
