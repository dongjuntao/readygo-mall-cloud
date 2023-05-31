package com.mall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.member.entity.CollectGoodsEntity;
import com.mall.member.entity.RecipientInfoEntity;

import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 商品收藏service
 * @Date 2022/5/2 10:52
 * @Version 1.0
 */
public interface CollectGoodsService extends IService<CollectGoodsEntity> {

    /**
     * 新增商品收藏
     * @param collectGoods
     * @return
     */
    int saveCollectGoods(CollectGoodsEntity collectGoods);

    /**
     * 取消商品收藏
     * @param memberId 会员id
     * @param goodsId 商品id
     * @return
     */
    int deleteCollectGoods(Long memberId, Long goodsId);

    /**
     * 判断是否已收藏
     * @param memberId 会员id
     * @param goodsId
     * @return
     */
    boolean isCollected(Long memberId, Long goodsId);

    /**
     * 查询所有收藏商品
     * @param memberId
     * @return
     */
    List<CollectGoodsEntity> listAll(Long memberId);
}
