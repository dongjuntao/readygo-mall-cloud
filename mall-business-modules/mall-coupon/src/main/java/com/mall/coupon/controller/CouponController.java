package com.mall.coupon.controller;

import cn.hutool.core.bean.BeanUtil;
import com.mall.admin.api.feign.FeignAdminUserService;
import com.mall.common.base.CommonResult;
import com.mall.common.base.enums.ResultCodeEnum;
import com.mall.common.base.utils.PageUtil;
import com.mall.coupon.entity.CouponEntity;
import com.mall.coupon.service.CouponService;
import com.mall.member.api.back.FeignBackCouponReceivedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
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

    @Autowired
    private FeignAdminUserService feignAdminUserService;

    @Autowired
    private FeignBackCouponReceivedService feignBackCouponReceivedService;

    /**
     * 所有优惠券列表
     */
    @GetMapping("/list")
    public CommonResult list(@RequestParam(value = "pageNum", required = false) Integer pageNum,
                             @RequestParam(value = "pageSize", required = false) Integer pageSize,
                             @RequestParam(value = "name", required = false) String name,
                             @RequestParam(value = "adminUserId", required = false) Long adminUserId,
                             @RequestParam(value = "authStatus", required = false) Integer authStatus){
        PageUtil page = couponService.getByPage(pageNum, pageSize,name,adminUserId,authStatus);
        //根据分页结果查询商品信息，并设置属性
        List list = page.getList();
        if (list.size() == 0) {
            return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), page);
        }
        List<Long> adminUserIds = new ArrayList<>();
        for (int i=0; i<list.size(); i++) {
            CouponEntity coupon = (CouponEntity) list.get(i);
            if (coupon.getAdminUserId() != null) {
                adminUserIds.add(coupon.getAdminUserId());
            }
        }
        //远程调用admin服务，获取商户信息
        CommonResult result = feignAdminUserService.listByIds(adminUserIds.toArray(new Long[adminUserIds.size()]));
        if (result != null && "200".equals(result.getCode())) {
            List resultList = (List) result.getData();
            for (int i=0;i<list.size();i++) {
                for(int j=0; j<resultList.size(); j++) {
                    Map<String,Object> map = (Map)resultList.get(j);
                    if (((CouponEntity)list.get(i)).getAdminUserId() != null &&
                            ((CouponEntity)list.get(i)).getAdminUserId().toString().equals(map.get("id").toString())) {
                        ((CouponEntity) list.get(i)).setMerchantName(map.get("name").toString());
                        break;//一旦找到，退出当前循环，减少循环次数
                    }
                }
            }
        }

        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), page);
    }

    /**
     * 保存优惠券
     * @param couponEntity
     * @return
     */
    @PostMapping("save")
    public CommonResult saveCoupon(@RequestBody CouponEntity couponEntity) {
        //如果是平台优惠券，不需要审核，直接为已通过状态, 否则为待审核状态
        if (couponEntity.getSource() == 0) {
            couponEntity.setAuthStatus(1);
        }else {
            couponEntity.setAuthStatus(0);
        }
        couponEntity.setReceivedNumber(0);//已领取的数量为0
        return couponService.saveCoupon(couponEntity) > 0 ? CommonResult.success() : CommonResult.fail();
    }

    /**
     * 修改优惠券
     * @param couponEntity
     * @return
     */
    @PutMapping("update")
    public CommonResult updateCoupon(@RequestBody CouponEntity couponEntity) {
        int count = couponService.updateCoupon(couponEntity);
        if (count == -1) {
            return CommonResult.success(ResultCodeEnum.COUPON_ISSUE_NUMBER_VALID.getCode(),ResultCodeEnum.COUPON_ISSUE_NUMBER_VALID.getMessage(), couponEntity);
        }
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
     * 修改优惠券状态
     * @param couponId 优惠券id
     * @param status 状态
     * @return
     */
    @PutMapping("updateStatus")
    public CommonResult updateStatus(@RequestParam("couponId") Long couponId,
                                     @RequestParam("status") Boolean status) {
        return couponService.updateStatus(couponId, status) > 0 ? CommonResult.success() : CommonResult.fail();
    }

    /**
     * 优惠券审核
     * @param couponId 优惠券id
     * @param authStatus 审核状态【1:通过 2：拒绝】
     * @param authOpinion 审核意见【如果拒绝，请必填】
     * @return
     */
    @PutMapping("auth")
    public CommonResult auth(@RequestParam("couponId") Long couponId,
                             @RequestParam("authStatus") Integer authStatus,
                             @RequestParam("authOpinion") String authOpinion) {
        return couponService.auth(couponId, authStatus, authOpinion) > 0 ? CommonResult.success() : CommonResult.fail();
    }

    /**
     * 获取优惠券领取列表
     */
    @GetMapping("getCouponReceivedList")
    public CommonResult getCouponReceivedList(@RequestParam(value = "pageNum", required = false) Integer pageNum,
                                              @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                              @RequestParam(value = "couponId", required = false) Long couponId){
        CommonResult result = feignBackCouponReceivedService.getCouponReceivedList(pageNum, pageSize, couponId);
        PageUtil resultPage = null;
        if (result != null && "200".equals(result.getCode())) {
            resultPage = BeanUtil.copyProperties(result.getData(), PageUtil.class);
        }
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), resultPage);
    }
}
