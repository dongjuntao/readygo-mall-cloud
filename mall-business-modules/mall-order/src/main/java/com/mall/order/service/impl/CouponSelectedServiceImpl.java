package com.mall.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.order.entity.CouponSelectedEntity;
import com.mall.order.mapper.CouponSelectedMapper;
import com.mall.order.service.CouponSelectedService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author DongJunTao
 * @Description 购物车中已选中的优惠券Service实现类
 * @Date 2022/6/5 15:29
 * @Version 1.0
 */
@Service("couponSelectedService")
public class CouponSelectedServiceImpl extends ServiceImpl<CouponSelectedMapper, CouponSelectedEntity>
        implements CouponSelectedService {

    /**
     * 选中优惠券
     * @param cartCouponSelected
     * @return
     */
    @Override
    public int select(CouponSelectedEntity cartCouponSelected, Boolean use) {
        QueryWrapper<CouponSelectedEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(cartCouponSelected.getMemberId() != null, "member_id", cartCouponSelected.getMemberId());
        CouponSelectedEntity old = this.getOne(queryWrapper);
        int count;
        if (use) {
            if (old != null) {
                old.setReceivedCouponId(cartCouponSelected.getReceivedCouponId());
                count = baseMapper.updateById(old);
            } else {
                count = baseMapper.insert(cartCouponSelected);
            }
        } else {
            count = baseMapper.deleteById(old.getId());
        }

        return count;
    }

    @Override
    public List<CouponSelectedEntity> getSelected(Long memberId) {
        QueryWrapper<CouponSelectedEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(memberId != null, "member_id", memberId);
        return baseMapper.selectList(queryWrapper);
    }
}
