package com.mall.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.common.base.utils.CurrentUserContextUtil;
import com.mall.member.entity.CollectGoodsEntity;
import com.mall.member.entity.RecipientInfoEntity;
import com.mall.member.mapper.CollectGoodsMapper;
import com.mall.member.service.CollectGoodsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 商品收藏service实现类
 * @Date 2022/5/2 10:55
 * @Version 1.0
 */
@Service("collectGoodsService")
public class CollectGoodsServiceImpl extends ServiceImpl<CollectGoodsMapper, CollectGoodsEntity> implements CollectGoodsService {

    /**
     * 新增商品收藏
     * @param collectGoods
     * @return
     */
    @Override
    public int saveCollectGoods(CollectGoodsEntity collectGoods) {
        //判断商品是否已经收藏
        QueryWrapper<CollectGoodsEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(collectGoods.getMemberId() != null, "member_id", collectGoods.getMemberId());
        queryWrapper.eq(collectGoods.getGoodsId() != null, "goods_id", collectGoods.getGoodsId());
        if (baseMapper.selectOne(queryWrapper) != null){
            return -1;
        }
        return baseMapper.insert(collectGoods);
    }

    /**
     * 取消商品收藏
     * @param memberId 会员id
     * @param goodsId 商品id
     * @return
     */
    @Override
    public int deleteCollectGoods(Long memberId, Long goodsId) {
        QueryWrapper<CollectGoodsEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(memberId != null, "member_id", memberId);
        queryWrapper.eq(goodsId != null, "goods_id", goodsId);
        return baseMapper.delete(queryWrapper);
    }

    /**
     * 判断是否已收藏
     * @param memberId 会员id
     * @param goodsId 商品id
     * @return
     */
    @Override
    public boolean isCollected(Long memberId, Long goodsId) {
        QueryWrapper<CollectGoodsEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(memberId != null, "member_id", memberId);
        queryWrapper.eq(goodsId != null, "goods_id", goodsId);
        return baseMapper.selectOne(queryWrapper) == null ? false : true;
    }

    /**
     * 查询所有的商品收藏
     * @param params
     * @return
     */
    @Override
    public List<CollectGoodsEntity> listAll(Map<String, Object> params) {
        Long memberId = params.get("userId") == null ? null: Long.valueOf((params.get("userId").toString()));
        QueryWrapper<CollectGoodsEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(memberId != null, "member_id", memberId);
        return this.baseMapper.selectList(queryWrapper);
    }
}
