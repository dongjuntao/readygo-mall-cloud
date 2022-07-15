package com.mall.payment.controller;

import com.alipay.api.AlipayApiException;
import com.mall.common.base.CommonResult;
import com.mall.order.api.feign.FeignTradeService;
import com.mall.payment.service.AlipayService;
import com.mall.payment.vo.CreateQrParamsVO;
import com.mall.payment.vo.PayInfoVO;
import com.mall.payment.vo.PayResultVO;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 支付宝支付
 * @Date 2022/6/26 15:13
 * @Version 1.0
 */
@RestController
@RequestMapping("alipay")
public class AlipayController {

    @Autowired
    private AlipayService alipayService;

    @Autowired
    private FeignTradeService feignTradeService;

    /**
     * 创建支付二维码信息
     * @param tradeCode 交易号
     * @param orderCode 订单号
     * @return
     */
    @GetMapping("createQR")
    public CommonResult getPayInfo(@RequestParam(value = "tradeCode", required = false) String tradeCode,
                                   @RequestParam(value = "orderCode", required = false) String orderCode,
                                   @RequestParam("orderType") String orderType) throws UnsupportedEncodingException {
        Map<String, Object> params = new HashMap<>();
        params.put("code", tradeCode);
        String price = ""; //价格
        String subject = ""; //订单说明
        PayInfoVO payInfoVO = null;
        //提交订单后立马支付（一个交易可能包含多个订单）
        if ("TRADE".equals(orderType)) {
            CommonResult result = feignTradeService.getTradeByParams(params);
            Map<String, Object> resultMap = (Map<String, Object>)result.getData();
            subject = tradeCode;
            price = String.valueOf(resultMap.get("finalPrice"));
            payInfoVO = alipayService.createQR(tradeCode, price, subject);
        } else if ("ORDER".equals(orderType)) { //后续在订单列表指定某个订单进行支付
            CommonResult result = feignTradeService.getTradeDetailByParams(params);
            Map<String, Object> resultMap = (Map<String, Object>)result.getData();
            List resultList = (List) resultMap.get("orderList");
            Map<String,Object> resMap = (Map<String,Object>)resultList.stream().filter(res -> ((Map<String,Object>) res).get("code").toString().equals(orderCode)).findFirst().get();
            subject = orderCode;
            price = resMap.get("finalPrice").toString();
            payInfoVO = alipayService.createQR(orderCode, price, subject);
        }
        return CommonResult.success(payInfoVO);
    }

    @PostMapping(value = "notify")
    @GlobalTransactional(rollbackFor = Exception.class)
    public String tradeNotify(HttpServletRequest request) {
        String result = "failure";
        try {
            //验签
            Map<String,String> params = new HashMap<>();
            Boolean signVerified = alipayService.signVerified(request,params);
            if(!signVerified) {
                return result; //验签失败则记录异常日志，并在response中返回failure.
            }
            //校验支付宝返回参数
            alipayService.checkParams(params);
            //在支付宝的业务通知中，只有交易通知状态为 TRADE_SUCCESS 或 TRADE_SUCCESS 支付宝才会认定为买家付款成功。
            String tradeStatus = params.get("trade_status");
            if("TRADE_SUCCESS".equals(tradeStatus)){
                result = "success";
            } else {
                return result;
            }
        } catch (AlipayApiException e) {
            return result;
        }
        //付款成功后，需要修改订单状态，优惠券更新，商品库存扣减，请见AplPaySuccessAspect
        return result;
    }

    /**
     * 获取交易（或订单）结果
     * @param code 交易号 或 订单号【可能存在整笔交易支付，或者单个订单支付，整笔交易可能能包含多个订单】
     * @return
     */
    @GetMapping("payResult")
    public CommonResult getAlipayResult(@RequestParam("code") String code) {
        try{
            PayResultVO payResult = alipayService.getAlipayResult(code);
            return CommonResult.success(payResult);
        }catch (AlipayApiException e) {}
        return CommonResult.fail();
    }

}
