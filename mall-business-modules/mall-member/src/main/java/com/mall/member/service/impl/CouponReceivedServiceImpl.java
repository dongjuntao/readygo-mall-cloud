package com.mall.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.common.base.utils.CurrentUserContextUtil;
import com.mall.common.base.utils.PageBuilder;
import com.mall.common.base.utils.PageUtil;
import com.mall.member.entity.CouponReceivedEntity;
import com.mall.member.entity.FootprintEntity;
import com.mall.member.mapper.CouponReceivedMapper;
import com.mall.member.service.CouponReceivedService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 我的优惠券service实现
 * @Date 2022/6/4 15:21
 * @Version 1.0
 */
@Service("couponReceivedService")
public class CouponReceivedServiceImpl extends ServiceImpl<CouponReceivedMapper, CouponReceivedEntity>
        implements CouponReceivedService {

    @Override
    public int saveCouponReceived(CouponReceivedEntity couponReceived) {
        return baseMapper.insert(couponReceived);
    }

    @Override
    public PageUtil getByPage(Map<String, Object> params) {
        Page<CouponReceivedEntity> page = (Page<CouponReceivedEntity>)new PageBuilder<CouponReceivedEntity>().getPage(params);
        QueryWrapper<CouponReceivedEntity> wrapper = new QueryWrapper<>();
        Long memberId = params.get("memberId") == null ? null: Long.valueOf((params.get("memberId").toString()));
        Integer useStatus = params.get("useStatus") == null ? null:  Integer.valueOf(params.get("useStatus").toString());
        wrapper.eq(memberId != null,"member_id", memberId);
        wrapper.eq(useStatus != null, "use_status", useStatus);
        wrapper.orderByDesc("create_time");
        IPage<CouponReceivedEntity> iPage = baseMapper.selectPage(page, wrapper);
        return new PageUtil(iPage);
    }

    @Override
    public Integer count(Map<String, Object> params) {
        QueryWrapper<CouponReceivedEntity> queryWrapper = new QueryWrapper<>();
        Long memberId = params.get("memberId") == null ? null: Long.valueOf((params.get("memberId").toString()));
        Long couponId = params.get("couponId") == null ? null: Long.valueOf((params.get("couponId").toString()));
        queryWrapper.eq(memberId != null,"member_id", memberId);
        queryWrapper.eq(couponId != null,"coupon_id", couponId);
        return baseMapper.selectCount(queryWrapper);
    }

    @Override
    public List<CouponReceivedEntity> getListAll(Map<String, Object> params) {
        QueryWrapper<CouponReceivedEntity> wrapper = new QueryWrapper<>();
        Long memberId = params.get("memberId") == null ? null: Long.valueOf((params.get("memberId").toString()));
        Integer useStatus = params.get("useStatus") == null ? null:  Integer.valueOf(params.get("useStatus").toString());
        wrapper.eq(memberId != null,"member_id", memberId);
        wrapper.eq(useStatus != null, "use_status", useStatus);
        wrapper.orderByDesc("create_time");
        return baseMapper.selectList(wrapper);
    }

    @Override
    public CouponReceivedEntity getById(Long id) {
        return baseMapper.selectById(id);
    }
}
