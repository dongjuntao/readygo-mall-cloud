package com.mall.coupon.controller;

import com.mall.common.base.CommonResult;
import com.mall.common.base.enums.ResultCodeEnum;
import com.mall.common.util.PageUtil;
import com.mall.coupon.entity.CouponEntity;
import com.mall.coupon.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 优惠券controller
 * @Date 2021/10/31 22:08
 * @Version 1.0
 */
@RestController
@RequestMapping("coupon")
public class CouponController {

    @Autowired
    private CouponService couponService;

    /**
     * 所有优惠券列表
     */
    @GetMapping("/list")
    public CommonResult list(@RequestParam Map<String, Object> params){
        PageUtil page = couponService.getByPage(params);
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), page);
    }

    /**
     * 保存优惠券
     * @param couponEntity
     * @return
     */
    @PostMapping("save")
    public CommonResult saveCoupon(@RequestBody CouponEntity couponEntity) {
        couponEntity.setRestNumber(couponEntity.getIssueNumber());//刚创建时优惠券剩余数量和发行数量一致
        return couponService.saveCoupon(couponEntity) > 0 ? CommonResult.success() : CommonResult.fail();
    }

    /**
     * 修改优惠券
     * @param couponEntity
     * @return
     */
    @PutMapping("update")
    public CommonResult updateCoupon(@RequestBody CouponEntity couponEntity) {
        return couponService.updateCoupon(couponEntity) > 0 ? CommonResult.success() : CommonResult.fail();
    }

    /**
     * 所有单个优惠券
     */
    @GetMapping("getCouponById")
    public CommonResult getCouponById(@RequestParam("couponId") Long couponId){
        CouponEntity couponEntity = couponService.getById(couponId);
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), couponEntity);
    }

    /**
     * 删除优惠券
     */
    @DeleteMapping("delete")
    public CommonResult delete(@RequestBody Long[] couponIds){
        couponService.deleteBatch(couponIds);
        return CommonResult.success();
    }

    /**
     * 修改优惠券
     * @param couponId 优惠券id
     * @param status 状态
     * @return
     */
    @PutMapping("updateStatus")
    public CommonResult updateStatus(@RequestParam("couponId") Long couponId,
                                     @RequestParam("status") Boolean status) {
        return couponService.updateStatus(couponId, status) > 0 ? CommonResult.success() : CommonResult.fail();
    }
}
