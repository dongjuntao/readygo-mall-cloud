package com.mall.payment.controller;

import com.alipay.api.AlipayApiException;
import com.mall.common.base.CommonResult;
import com.mall.payment.service.AlipayService;
import com.mall.payment.vo.PayInfoVO;
import com.mall.payment.vo.PayResultVO;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
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

    /**
     * 创建支付二维码信息
     * @param tradeCode
     * @param price
     * @param subject
     * @return
     */
    @GetMapping("createQR")
    public CommonResult getPayInfo(@RequestParam("tradeCode") String tradeCode,
                                   @RequestParam("price") String price,
                                   @RequestParam(value = "subject") String subject) throws UnsupportedEncodingException {
        PayInfoVO payInfoVO = alipayService.createQR(tradeCode, price, subject);
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
     * 获取交易结果
     * @param tradeCode
     * @return
     */
    @GetMapping("tradeResult")
    public CommonResult getAlipayResult(@RequestParam("tradeCode") String tradeCode) {
        try{
            PayResultVO payResult = alipayService.getAlipayResult(tradeCode);
            return CommonResult.success(payResult);
        }catch (AlipayApiException e) {}
        return CommonResult.fail();
    }

}
