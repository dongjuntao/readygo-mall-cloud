package com.mall.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.member.entity.CollectGoodsEntity;
import com.mall.member.entity.CollectShopEntity;
import com.mall.member.mapper.CollectShopMapper;
import com.mall.member.service.CollectShopService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 店铺收藏service实现类
 * @Date 2022/5/2 10:56
 * @Version 1.0
 */
@Service("collectShopService")
public class CollectShopServiceImpl extends ServiceImpl<CollectShopMapper, CollectShopEntity> implements CollectShopService {

    /**
     * 新增店铺收藏
     * @param collectShop
     * @return
     */
    @Override
    public int saveCollectShop(CollectShopEntity collectShop) {
        return baseMapper.insert(collectShop);
    }

    /**
     * 取消店铺收藏
     * @param memberId 会员id
     * @param merchantId 店铺（商户）id
     * @return
     */
    @Override
    public int deleteCollectShop(Long memberId, Long merchantId) {
        QueryWrapper<CollectShopEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(memberId != null, "member_id", memberId);
        queryWrapper.eq(merchantId != null, "merchant_id", merchantId);
        return baseMapper.delete(queryWrapper);
    }

    /**
     * 判断是否已收藏
     * @param memberId 会员id
     * @param merchantId 店铺id
     * @return
     */
    @Override
    public boolean isCollected(Long memberId, Long merchantId) {
        QueryWrapper<CollectShopEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(memberId != null, "member_id", memberId);
        queryWrapper.eq(merchantId != null, "merchant_id", merchantId);
        return baseMapper.selectOne(queryWrapper) == null ? false : true;
    }

    /**
     * 查询所有的店铺收藏
     * @param memberId
     * @return
     */
    @Override
    public List<CollectShopEntity> listAll(Long memberId) {
        QueryWrapper<CollectShopEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(memberId != null, "member_id", memberId);
        return this.baseMapper.selectList(queryWrapper);
    }
}
