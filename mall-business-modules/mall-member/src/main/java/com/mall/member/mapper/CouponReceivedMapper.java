package com.mall.member.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.member.entity.CouponReceivedEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author DongJunTao
 * @Description 我的优惠券
 * @Date 2022/6/4 15:19
 * @Version 1.0
 */
@Mapper
public interface CouponReceivedMapper extends BaseMapper<CouponReceivedEntity> {

    /**
     * 分页查询优惠券信息，包括部分会员信息
     * @param page
     * @param wrapper
     * @return
     */
    IPage<CouponReceivedEntity> queryPageWithMemberInfo(
            @Param("page") Page<CouponReceivedEntity> page,
            @Param(Constants.WRAPPER) QueryWrapper<CouponReceivedEntity> wrapper);
}
