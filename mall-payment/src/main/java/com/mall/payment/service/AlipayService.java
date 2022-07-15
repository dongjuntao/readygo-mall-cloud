package com.mall.payment.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.internal.util.StringUtils;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.mall.common.base.CommonResult;
import com.mall.common.base.utils.CurrentUserContextUtil;
import com.mall.order.api.feign.FeignOrderService;
import com.mall.order.api.feign.FeignTradeService;
import com.mall.payment.vo.PayInfoVO;
import com.mall.payment.vo.PayResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2022/6/26 15:09
 * @Version 1.0
 */
@Service
public class AlipayService {

    @Value("${alipay.app-id}")
    private String appId;

    @Value("${alipay.private-key}")
    private String privateKey;

    @Value("${alipay.public-key}")
    private String publicKey;

    @Value("${alipay.gateway}")
    private String gateway;

    @Value("${alipay.sign-type}")
    private String signType;

    @Value("${alipay.charset}")
    private String charset;

    @Value("${alipay.format}")
    private String format;

    @Value("${alipay.notify-url}")
    private String notifyUrl;

    private AlipayClient alipayClient;

    @Autowired
    private FeignTradeService feignTradeService;

    @Autowired
    private FeignOrderService feignOrderService;

    @PostConstruct
    public void init() {
        alipayClient = new DefaultAlipayClient(gateway, appId, privateKey, format, charset, publicKey, signType);
    }

    /**
     * 创建支付二维码信息
     * @param tradeCode
     * @param price
     * @param subject
     * @return
     */
    public PayInfoVO createQR(String tradeCode, String price, String subject) throws UnsupportedEncodingException {
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        request.setNotifyUrl(notifyUrl);
        String passback_params = "userName="+CurrentUserContextUtil.getCurrentUserInfo().getUserName()+"&userId="+CurrentUserContextUtil.getCurrentUserInfo().getUserId();
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", tradeCode);
        bizContent.put("total_amount", price);
        bizContent.put("subject", subject);
        bizContent.put("timeout_express", "30m");
        bizContent.put("passback_params", URLEncoder.encode(passback_params, "UTF-8"));
        request.setBizContent(bizContent.toString());

        AlipayTradePrecreateResponse response = null;
        //支付宝二维码URL
        String qrCode = "";
        String body = "";
        try {
            response = alipayClient.execute(request);
            if (!response.isSuccess()) {
                throw new RuntimeException("alipay exception");
            }
            qrCode = response.getQrCode();
            body = response.getBody();
        } catch (AlipayApiException e) {
        }
        // 封装支付信息 返回
        PayInfoVO payInfoVO = new PayInfoVO();
        payInfoVO.setTradeCode(tradeCode);
        payInfoVO.setPrice(price);
        payInfoVO.setSubject(subject);
        payInfoVO.setUrl(qrCode);
        payInfoVO.setBody(body);
        return payInfoVO;
    }

    /**
     * 验签
     * @param request
     * @return
     */
    public Boolean signVerified(HttpServletRequest request, Map<String,String> params) throws AlipayApiException {
        getParamsMap(request, params);
        return AlipaySignature.rsaCheckV1(params, publicKey, charset, signType);
    }

    /**
     * 获取参数（Map形式）
     * @param request
     * @param params
     * @return
     */
    public Map<String, String> getParamsMap (HttpServletRequest request, Map<String,String> params) {
        //获取支付宝POST过来反馈信息
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = iter.next();
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        return params;
    }

    /**
     * 校验支付宝返回参数
     * @param params
     * @throws AlipayApiException
     */
    public void checkParams(Map<String, String> params) throws AlipayApiException {
        //1、查询交易号(out_trade_no)，查不到则交易失败
        String outTradeNo = params.get("out_trade_no");
        Map<String, Object> remoteParams = new HashMap<>();
        remoteParams.put("code", outTradeNo);
        CommonResult commonResult = null;
        //交易号
        if (outTradeNo.startsWith("T")) { //提交订单后立马支付（一个交易可能包含多个订单）
            commonResult = feignTradeService.getTradeByParams(remoteParams);//远程调用获取交易信息
        } else if (outTradeNo.startsWith("O")) { //订单号，后续在订单列表指定某个订单进行支付
            commonResult = feignOrderService.getOrderByParams(remoteParams);
        }
        if (commonResult == null || !"200".equals(commonResult.getCode()) ) {
            throw new AlipayApiException("out_trade_no is not valid");
        }
        //2、比较金额(total_amount)
        Map<String,Object> commonResultMap = (Map<String,Object>)commonResult.getData();
        if (!params.get("total_amount").equals(commonResultMap.get("finalPrice").toString())) {
            throw new AlipayApiException("total_amount not valid");
        }
        //3、校验应用id(appId)
        if (!appId.equals(params.get("app_id"))) {
            throw new AlipayApiException("app_id not valid");
        }
    }

    /**
     * 获取交易支付结果
     * @param tradeCode
     * @return
     * @throws AlipayApiException
     */
    public PayResultVO getAlipayResult(String tradeCode) throws AlipayApiException {
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", tradeCode);
        request.setBizContent(bizContent.toString());
        AlipayTradeQueryResponse response = alipayClient.execute(request);
        if(response.isSuccess()){
            PayResultVO payResultVO = new PayResultVO();
            payResultVO.setCode(response.getCode());
            payResultVO.setMsg(response.getMsg());
            payResultVO.setTradeStatus(response.getTradeStatus());
            payResultVO.setOutTradeNo(response.getOutTradeNo());
            return payResultVO;
        } else {
            return new PayResultVO();
        }
    }
}
