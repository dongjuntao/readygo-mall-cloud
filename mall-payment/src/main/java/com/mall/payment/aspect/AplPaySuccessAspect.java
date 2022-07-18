package com.mall.payment.aspect;

import com.alibaba.fastjson.JSON;
import com.mall.common.base.CommonResult;
import com.mall.common.base.utils.CurrentUserContextUtil;
import com.mall.goods.api.front.FeignFrontGoodsService;
import com.mall.member.api.FeignCouponReceivedService;
import com.mall.order.api.feign.FeignCouponSelectedService;
import com.mall.order.api.feign.FeignOrderService;
import com.mall.order.api.feign.FeignTradeService;
import com.mall.payment.service.AlipayService;
import io.seata.core.context.RootContext;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 支付宝扫描PC端二维码支付成功后，通知各系统更新时间
 * @Date 2022/4/21 16:22
 * @Version 1.0
 */
@Component
@Aspect
public class AplPaySuccessAspect {

    @Autowired
    private AlipayService alipayService;

    @Autowired
    private FeignOrderService feignOrderService;

    @Autowired
    private FeignTradeService feignTradeService;

    @Autowired
    private FeignCouponReceivedService feignCouponReceivedService;

    @Autowired
    private FeignCouponSelectedService feignCouponSelectedService;

    @Autowired
    private FeignFrontGoodsService feignFrontGoodsService;


    /**
     * 切点【收到支付成功的异步通知】
     */
    @Pointcut("execution(* com.mall.payment.controller.AlipayController.tradeNotify(..))")
    private void alipaySuccessAspect() {}

    /**
     * 拦截controller,发送消息通知
     * @param returnVal 返回值
     */
    @AfterReturning(value = "alipaySuccessAspect()", returning = "returnVal")
    private void sendAlipaySuccessInfo(Object returnVal){
        try {
            //如果支付成功，【修改订单状态，商品库存扣减，更新优惠券状态】，需要调用订单服务，商品服务，优惠券服务
            Map<String, String> paramsMap = new HashMap<>();
            if (returnVal != null && "success".equals(String.valueOf(returnVal))) {
                HttpServletRequest request=((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                alipayService.getParamsMap(request, paramsMap);

                String outTradeNo = paramsMap.get("out_trade_no");//商户端订单号
                //修改订单状态（T开头，表示交易号，包含多个商铺订单一起支付，O开头表示订单号，单个商铺支付）
                CommonResult orderResult = null;
                if (outTradeNo.startsWith("T")) {
                    feignTradeService.updateTradeStatus(outTradeNo, "PAID");
                    orderResult = feignOrderService.getSkuIdAndCount(outTradeNo, "TRADE");
                } else if (outTradeNo.startsWith("O")) {
                    feignOrderService.updateOrderStatus(outTradeNo, "PAID");
                    orderResult = feignOrderService.getSkuIdAndCount(outTradeNo, "ORDER");
                }
                ArrayList<Map<String,Object>> reduceStockMapList = new ArrayList<>();
                if (orderResult != null && "200".equals(orderResult.getCode())) {
                    List orderList = (List)orderResult.getData();
                    for (int i=0; i<orderList.size(); i++) {
                        Map<String, Object> orderListMap = (Map<String, Object>)orderList.get(i);
                        Map<String, Object> reduceStockParams = new HashMap<>();
                        reduceStockParams.put("skuId", Long.valueOf(String.valueOf(orderListMap.get("skuId"))));
                        reduceStockParams.put("count", Integer.valueOf(String.valueOf(orderListMap.get("count"))));
                        reduceStockMapList.add(reduceStockParams);
                    }
                }
                //批量减库存
                feignFrontGoodsService.batchReduceStock(reduceStockMapList);

                //先查询交易时选中的优惠券信息，据此查询用户优惠券信息，并修改使用状态
                //TODO 后续修改
                Map<String, Object> map = new HashMap<>();
                map.put("userName", "test");
                map.put("userId", 1);
                CommonResult couponSelectedResult = feignCouponSelectedService.getSelected(JSON.toJSONString(map));
                if (couponSelectedResult != null && "200".equals(couponSelectedResult.getCode())) {
                    if (couponSelectedResult.getData() != null) {
                        Map<String, Object> result = (Map<String, Object>)couponSelectedResult.getData();
                        Long receivedCouponId = Long.valueOf(String.valueOf(result.get("receivedCouponId")));
                        //用户优惠券改为已使用
                        feignCouponReceivedService.updateUseStatus(receivedCouponId, 1);
                    }
                    //此时已选择的优惠券需要物理删除
                    feignCouponSelectedService.deleteCouponSelected(JSON.toJSONString(map));
                }
            }
        } catch (Exception e) {
            //TODO 事务回滚 该订单 显示交易失败，后面可以协调用户进行退款处理, 另外，用掉的优惠券需要恢复
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

}
