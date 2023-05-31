package com.mall.member.controller;

import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.mall.common.base.CommonResult;
import com.mall.common.base.enums.ResultCodeEnum;
import com.mall.common.base.utils.CurrentUserContextUtil;
import com.mall.common.base.utils.DateUtil;
import com.mall.common.base.utils.PageUtil;
import com.mall.coupon.api.feign.FeignCouponService;
import com.mall.coupon.api.feign.front.FeignFrontCouponService;
import com.mall.member.entity.CouponReceivedEntity;
import com.mall.member.service.CouponReceivedService;
import com.mall.member.vo.CouponReceivedVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.*;

/**
 * @Author DongJunTao
 * @Description 我的优惠券
 * @Date 2022/6/4 15:22
 * @Version 1.0
 */
@RestController
@RequestMapping("coupon/received")
public class CouponReceivedController {

    @Autowired
    private FeignCouponService feignCouponService;

    @Autowired
    private CouponReceivedService couponReceivedService;

    @Autowired
    private FeignFrontCouponService feignFrontCouponService;

    /**
     * 分页查询我的优惠券
     */
    @GetMapping("/list")
    public CommonResult list(@RequestParam(value = "pageNum",required = false) Integer pageNum,
                             @RequestParam(value = "pageSize",required = false) Integer pageSize,
                             @RequestParam(value = "useStatus",required = false) Integer useStatus){
        Long memberId = CurrentUserContextUtil.getCurrentUserInfo().getUserId();
        List<CouponReceivedVO> couponReceivedVOList = new ArrayList<>();
        PageUtil page = couponReceivedService.getByPage(pageNum,pageSize,memberId,useStatus);
        List couponList = page.getList();
        Long[] couponIdArr = new Long[couponList.size()];//获取所以的优惠券id,通过优惠券微服务，查询优惠券相关信息
        for (int i=0; i<couponList.size(); i++) {
            CouponReceivedEntity couponReceived = (CouponReceivedEntity)couponList.get(i);
            couponIdArr[i] = couponReceived.getCouponId();
        }
        CommonResult result = feignFrontCouponService.getCouponById(couponIdArr);
        if (result == null || !"200".equals(result.getCode())) {
            return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), new ArrayList<>());
        }
        List resultList = (List) result.getData();
        for (int i=0; i<couponList.size(); i++) {
            CouponReceivedEntity couponReceived = (CouponReceivedEntity) couponList.get(i);
            for (int j=0; j<resultList.size(); j++) {
                Map<String,Object> map = (Map)resultList.get(j);
                if (couponReceived.getCouponId().equals(Long.valueOf(String.valueOf(map.get("id"))))) {
                    CouponReceivedVO couponReceivedVO = new CouponReceivedVO();
                    couponReceivedVO.setId(couponReceived.getId());
                    couponReceivedVO.setCouponId(couponReceived.getCouponId());
                    couponReceivedVO.setName(String.valueOf(map.get("name")));
                    couponReceivedVO.setType(Integer.valueOf(String.valueOf(map.get("type"))));
                    couponReceivedVO.setMinConsumption(Double.valueOf(String.valueOf(map.get("minConsumption"))));
                    couponReceivedVO.setDiscountAmount(Double.valueOf(String.valueOf(map.get("discountAmount"))));
                    couponReceivedVO.setSource(Integer.valueOf(String.valueOf(map.get("source"))));
                    couponReceivedVO.setUseScope(Integer.valueOf(String.valueOf(map.get("useScope"))));
                    couponReceivedVO.setMerchantName(String.valueOf(map.get("merchantName")));
                    try {
                        couponReceivedVO.setValidPeriodStart(DateUtil.getDateByDateString(String.valueOf(map.get("validPeriodStart")),"yyyy-MM-dd hh:mm:ss"));
                        couponReceivedVO.setValidPeriodEnd(DateUtil.getDateByDateString(String.valueOf(map.get("validPeriodEnd")),"yyyy-MM-dd hh:mm:ss"));
                    }catch (ParseException e){}
                    couponReceivedVOList.add(couponReceivedVO);
                    break;
                }
            }
        }
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), couponReceivedVOList);
    }

    /**
     * 查询我的优惠券列表（不分页）
     */
    @GetMapping("/listAll")
    public CommonResult listAll(@RequestParam Map<String, Object> params) {
        List<CouponReceivedVO> couponReceivedVOList = new ArrayList<>();
        List<CouponReceivedEntity> couponReceivedList = couponReceivedService.getListAll(params);
        Long[] couponIdArr = new Long[couponReceivedList.size()];//获取所以的优惠券id,通过优惠券微服务，查询优惠券相关信息
        for (int i=0; i<couponReceivedList.size(); i++) {
            couponIdArr[i] = couponReceivedList.get(i).getCouponId();
        }
        CommonResult result = feignFrontCouponService.getCouponById(couponIdArr);
        if (result == null || !"200".equals(result.getCode())) {
            return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), new ArrayList<>());
        }
        List resultList = (List) result.getData();
        for (int i=0; i<couponReceivedList.size(); i++) {
            CouponReceivedEntity couponReceived = couponReceivedList.get(i);
            for (int j=0; j<resultList.size(); j++) {
                Map<String,Object> map = (Map)resultList.get(j);
                if (couponReceived.getCouponId().equals(Long.valueOf(String.valueOf(map.get("id"))))) {
                    CouponReceivedVO couponReceivedVO = new CouponReceivedVO();
                    couponReceivedVO.setId(couponReceived.getId());
                    couponReceivedVO.setCouponId(couponReceived.getCouponId());
                    couponReceivedVO.setName(String.valueOf(map.get("name")));
                    couponReceivedVO.setType(Integer.valueOf(String.valueOf(map.get("type"))));
                    couponReceivedVO.setMinConsumption(Double.valueOf(String.valueOf(map.get("minConsumption"))));
                    couponReceivedVO.setDiscountAmount(Double.valueOf(String.valueOf(map.get("discountAmount"))));
                    couponReceivedVO.setSource(Integer.valueOf(String.valueOf(map.get("source"))));
                    couponReceivedVO.setUseScope(Integer.valueOf(String.valueOf(map.get("useScope"))));
                    couponReceivedVO.setMerchantName(String.valueOf(map.get("merchantName")));
                    try {
                        couponReceivedVO.setValidPeriodStart(DateUtil.getDateByDateString(String.valueOf(map.get("validPeriodStart")),"yyyy-MM-dd hh:mm:ss"));
                        couponReceivedVO.setValidPeriodEnd(DateUtil.getDateByDateString(String.valueOf(map.get("validPeriodEnd")),"yyyy-MM-dd hh:mm:ss"));
                    }catch (ParseException e){}
                    couponReceivedVOList.add(couponReceivedVO);
                    break;
                }
            }
        }
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), couponReceivedVOList);
    }

    /**
     * 优惠券领取
     * @param couponId
     * @return
     */
    @PostMapping("save")
    public CommonResult save(@RequestParam("couponId") Long couponId) throws ParseException {
        //查询商家发布的优惠券
        CommonResult result = feignCouponService.getCouponById(couponId);
        if (result == null || !"200".equals(result.getCode())) {
            return CommonResult.success(ResultCodeEnum.COUPON_INVALID.getCode(),ResultCodeEnum.COUPON_INVALID.getMessage());
        }
        Map resultMap = (Map) result.getData();
        CouponReceivedEntity couponReceived = new CouponReceivedEntity();
        couponReceived.setCreateTime(new Date()); //领取时间
        couponReceived.setUseStatus(0); //默认未使用
        couponReceived.setCouponId(couponId);
        couponReceived.setMemberId(CurrentUserContextUtil.getCurrentUserInfo().getUserId());
        couponReceived.setValidPeriodStart(DateUtil
                .getDateByDateString(String.valueOf(resultMap.get("validPeriodStart")), DateUtil.YYYY_MM_DD_HH_MM_SS));
        couponReceived.setValidPeriodEnd(DateUtil
                .getDateByDateString(String.valueOf(resultMap.get("validPeriodEnd")), DateUtil.YYYY_MM_DD_HH_MM_SS));
        //每人限领几张
        Integer perLimit = Integer.parseInt(String.valueOf(resultMap.get("perLimit")));
        Map<String, Object> params = new HashMap<>();
        params.put("memberId", CurrentUserContextUtil.getCurrentUserInfo().getUserId());
        params.put("couponId",couponId);
        Integer count = couponReceivedService.count(params);
        if (count>=perLimit) {
            return CommonResult.success(ResultCodeEnum.COUPON_RECEIVE_TOO_MANY_TIMES.getCode(),ResultCodeEnum.COUPON_RECEIVE_TOO_MANY_TIMES.getMessage());
        }
        //发行量和领取量
        Integer issueNumber = Integer.parseInt(String.valueOf(resultMap.get("issueNumber")));
        Integer receivedNumber = Integer.parseInt(String.valueOf(resultMap.get("receivedNumber")));
        if (issueNumber.equals(receivedNumber)) { //优惠券已被领完
            return CommonResult.success(ResultCodeEnum.COUPON_IS_EMPTY.getCode(),ResultCodeEnum.COUPON_IS_EMPTY.getMessage());
        }
        try {
            Date couponValidPeriodEnd = DateUtil.getDateByDateString(String.valueOf(resultMap.get("validPeriodEnd")),DateUtil.YYYY_MM_DD_HH_MM_SS);
            if (new Date().getTime() > couponValidPeriodEnd.getTime()) { //此时已过期
                return CommonResult.success(ResultCodeEnum.COUPON_IS_NOT_EXPIRED.getCode(),ResultCodeEnum.COUPON_IS_NOT_EXPIRED.getMessage());
            }
        }catch (ParseException e) {}
        return couponReceivedService.saveCouponReceived(couponReceived) > 0 ? CommonResult.success() : CommonResult.fail();
    }

    /**
     * 根据id查询我的优惠券
     */
    @GetMapping("getById")
    public CommonResult getById(@RequestParam("receivedCouponId") Long receivedCouponId) {
        CouponReceivedEntity couponReceived = couponReceivedService.getById(receivedCouponId);
        return CommonResult.success(couponReceived);
    }

    /**
     * 更新优惠券使用状态
     */
    @PutMapping("updateUseStatus")
    public CommonResult updateUseStatus(@RequestParam("receivedCouponId") Long receivedCouponId,
                                        @RequestParam("useStatus") Integer useStatus) {
        return couponReceivedService.updateUseStatus(receivedCouponId, useStatus) > 0 ?
                CommonResult.success() : CommonResult.fail();
    }
}
