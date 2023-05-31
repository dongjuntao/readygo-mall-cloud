package com.mall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.member.entity.CollectGoodsEntity;
import com.mall.member.entity.CollectShopEntity;

import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 店铺收藏service
 * @Date 2022/5/2 10:53
 * @Version 1.0
 */
public interface CollectShopService extends IService<CollectShopEntity> {

    /**
     * 新增店铺收藏
     * @param collectShop
     * @return
     */
    int saveCollectShop(CollectShopEntity collectShop);

    /**
     * 取消店铺收藏
     * @param memberId 会员id
     * @param merchantId 店铺（商户）id
     * @return
     */
    int deleteCollectShop(Long memberId, Long merchantId);

    /**
     * 判断是否已收藏
     * @param memberId 会员id
     * @param merchantId 店铺id
     * @return
     */
    boolean isCollected(Long memberId, Long merchantId);

    /**
     * 查询所有收藏店铺
     * @param memberId
     * @return
     */
    List<CollectShopEntity> listAll(Long memberId);
}
