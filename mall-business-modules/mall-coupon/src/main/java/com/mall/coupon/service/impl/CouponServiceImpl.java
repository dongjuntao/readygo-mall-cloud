package com.mall.coupon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.common.base.utils.PageBuilder;
import com.mall.common.base.utils.PageUtil;
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
        Page<CouponEntity> page = (Page<CouponEntity>)new PageBuilder<CouponEntity>().getPage(params);
        QueryWrapper<CouponEntity> wrapper = new QueryWrapper<>();
        String name = String.valueOf(params.get("name"));//商品名称
        Long adminUserId = params.get("adminUserId") == null ? null: Long.valueOf((params.get("adminUserId").toString()));
        Integer authStatus = params.get("authStatus") == null ? null : Integer.valueOf((params.get("authStatus").toString()));
        wrapper
                .like(StringUtils.isNotBlank(name), "c.name", name)
                .eq(adminUserId != null, "admin_user_id", adminUserId)
                .eq(authStatus != null, "auth_status", authStatus);
        IPage<CouponEntity> iPage = baseMapper.queryPage(page, wrapper);
        return new PageUtil(iPage);
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
    public int updateStatus(Long couponId, Boolean status) {
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

    /**
     * 优惠券审核
     * @param couponId
     * @param authStatus
     * @param authOpinion
     * @return
     */
    @Override
    public int auth(Long couponId, Integer authStatus, String authOpinion) {
        CouponEntity couponEntity = this.getById(couponId);
        if (couponEntity == null) {
            return -1;
        }
        couponEntity.setAuthStatus(authStatus);
        couponEntity.setAuthOpinion(authOpinion);
        return baseMapper.updateById(couponEntity);
    }
}
