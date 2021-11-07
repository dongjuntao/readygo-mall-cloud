package com.mall.coupon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.common.util.PageBuilder;
import com.mall.common.util.PageUtil;
import com.mall.coupon.entity.CouponEntity;
import com.mall.coupon.mapper.CouponMapper;
import com.mall.coupon.service.CouponService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 优惠券service实现类
 * @Date 2021/10/31 22:07
 * @Version 1.0
 */
@Service("couponService")
public class CouponServiceImpl extends ServiceImpl<CouponMapper, CouponEntity> implements CouponService {

    /**
     * 新增优惠券
     * @param couponEntity
     * @return
     */
    @Override
    public int saveCoupon(CouponEntity couponEntity) {
        return baseMapper.insert(couponEntity);
    }

    /**
     * 修改优惠券
     * @param couponEntity
     * @return
     */
    @Override
    public int updateCoupon(CouponEntity couponEntity) {
        return baseMapper.updateById(couponEntity);
    }

    /**
     * 分页查询优惠券列表
     * @param params
     * @return
     */
    @Override
    public PageUtil getByPage(Map<String, Object> params) {
        //物流公司名称
        String name = params.get("name") == null ? null : params.get("name").toString();
        IPage<CouponEntity> page = this.page(
                new PageBuilder<CouponEntity>().getPage(params),
                new QueryWrapper<CouponEntity>()
                        .like(StringUtils.isNotBlank(name), "name", name)
        );
        return new PageUtil(page);
    }

    /**
     * 删除优惠券（支持批量删除）
     * @param couponIds
     */
    @Override
    public void deleteBatch(Long[] couponIds) {
        this.removeByIds(Arrays.asList(couponIds));
    }

    @Override
    public int updateStatus(Long couponId, Integer status) {
        CouponEntity couponEntity = this.getById(couponId);
        if (couponEntity == null) {
            return -1;
        }
        couponEntity.setStatus(status);
        return baseMapper.updateById(couponEntity);
    }

    @Override
    public CouponEntity getById(Long couponId) {
        return baseMapper.selectById(couponId);
    }
}
