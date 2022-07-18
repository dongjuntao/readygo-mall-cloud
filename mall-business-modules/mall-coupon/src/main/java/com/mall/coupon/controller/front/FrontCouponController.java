package com.mall.coupon.controller.front;

import com.mall.admin.api.feign.FeignAdminUserService;
import com.mall.common.base.CommonResult;
import com.mall.common.base.enums.ResultCodeEnum;
import com.mall.common.base.utils.PageUtil;
import com.mall.coupon.entity.CouponEntity;
import com.mall.coupon.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 优惠券controller 供们门户端使用
 * @Date 2021/10/31 22:08
 * @Version 1.0
 */
@RestController
@RequestMapping("front/coupon")
public class FrontCouponController {

    @Autowired
    private CouponService couponService;

    @Autowired
    private FeignAdminUserService feignAdminUserService;

    /**
     * 所有优惠券列表
     */
    @GetMapping("/list")
    public CommonResult list(@RequestParam Long[] ids){
        List<CouponEntity> couponList = couponService.getBatchByIds(ids);
        if (couponList.size() == 0) {
            return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), couponList);
        }
        List<Long> adminUserIds = new ArrayList<>();
        for (int i=0; i<couponList.size(); i++) {
            CouponEntity coupon = couponList.get(i);
            if (coupon.getAdminUserId() != null) {
                adminUserIds.add(coupon.getAdminUserId());
            }
        }
        //远程调用admin服务，获取商户信息
        CommonResult result = feignAdminUserService.listByIds(adminUserIds.toArray(new Long[adminUserIds.size()]));
        if (result != null && "200".equals(result.getCode())) {
            List resultList = (List) result.getData();
            for (int i=0;i<couponList.size();i++) {
                for(int j=0; j<resultList.size(); j++) {
                    Map<String,Object> map = (Map)resultList.get(j);
                    if ((couponList.get(i)).getAdminUserId() != null &&
                            (couponList.get(i)).getAdminUserId().toString().equals(map.get("id").toString())) {
                        (couponList.get(i)).setMerchantName(map.get("name").toString());
                        break;//一旦找到，退出当前循环，减少循环次数
                    }
                }
            }
        }
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), couponList);
    }

    /**
     * 查询优惠券详细信息
     */
    @GetMapping("/getById")
    public CommonResult getById(@RequestParam("couponId") Long couponId){
        CouponEntity coupon = couponService.getById(couponId);
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), coupon);
    }

}
