package com.mall.order.controller;

import com.mall.admin.api.feign.front.FrontPayTypeService;
import com.mall.common.base.CommonResult;
import com.mall.order.entity.OrderEntity;
import com.mall.order.entity.TradeEntity;
import com.mall.order.service.OrderService;
import com.mall.order.service.TradeService;
import com.mall.order.vo.PayTypeVO;
import com.mall.order.vo.TradeDetailVO;
import com.mall.order.vo.TradeOrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @Author DongJunTao
 * @Description 交易controller
 * @Date 2022/6/16 19:51
 * @Version 1.0
 */
@RestController
@RequestMapping( "trade")
public class TradeController {

    @Autowired
    private TradeService tradeService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private FrontPayTypeService frontPayTypeService;
    /**
     * 创建交易（以及订单和子订单）
     * @param tradeDetailVO 订单信息
     * @return
     */
    @PostMapping("createTrade")
    public CommonResult createTrade(@RequestBody TradeDetailVO tradeDetailVO) {
        TradeEntity tradeEntity = tradeService.createTrade(tradeDetailVO);
        return CommonResult.success(tradeEntity);
    }

    /**
     * 支付页面交易信息
     * @param params 参数
     * @return
     */
    @GetMapping("tradePayInfo")
    public CommonResult tradePayInfo(@RequestParam Map<String, Object> params) {
        //订单类型 "TRADE", "ORDER"
        String orderType = params.get("orderType") == null ? null: params.get("orderType").toString();
        //如果是交易【从订单提交页支付】
        TradeOrderVO tradeOrderVO = new TradeOrderVO();
        if ("TRADE".equals(orderType)) {
            TradeEntity trade = tradeService.getTradeByParams(params);
            tradeOrderVO.setTradeOrOrderTime(trade.getTradeTime());
            tradeOrderVO.setFinalPrice(trade.getFinalPrice());
            tradeOrderVO.setOvertime(new Date(trade.getTradeTime().getTime() + (30*60*1000))); //交易过期时间（交易后30分钟内）
        } else if ("ORDER".equals(orderType)) { //如果是普通订单【从订单列表去支付】
            TradeEntity tradeDetail = tradeService.getTradeDetailByParams(params);
            String orderCode = params.get("orderCode") == null ? null: params.get("orderCode").toString();
            if (tradeDetail != null) {
                List<OrderEntity> orderList = tradeDetail.getOrderList();
                OrderEntity queryOrder = orderList.stream().filter(order -> order.getCode().equals(orderCode)).findFirst().get();
                tradeOrderVO.setTradeOrOrderTime(queryOrder.getCreateTime());
                tradeOrderVO.setFinalPrice(queryOrder.getFinalPrice());
                tradeOrderVO.setOvertime(new Date(queryOrder.getCreateTime().getTime() + (30*60*1000))); //交易过期时间（交易后30分钟内）
            }
        }

        Map<String, Object> remoteParams = new HashMap<>();
        remoteParams.put("enable", true);
        CommonResult result = frontPayTypeService.listAll(remoteParams);
        if (result != null && "200".equals(result.getCode())) {
            List<PayTypeVO> payTypeList = new ArrayList<>();
            List resultList = (List)result.getData();
            for (int i=0; i<resultList.size(); i++) {
                Map<String, Object> resultMap = ( Map<String, Object> )resultList.get(i);
                PayTypeVO payTypeVO = new PayTypeVO();
                payTypeVO.setType(String.valueOf(resultMap.get("type")));
                payTypeVO.setName(String.valueOf(resultMap.get("name")));
                payTypeVO.setLogo(String.valueOf(resultMap.get("logo")));
                payTypeList.add(payTypeVO);
            }
            tradeOrderVO.setPayTypeList(payTypeList);
        }
        return CommonResult.success(tradeOrderVO);
    }

    /**
     * 支付页面交易信息
     * @param params 参数
     * @return
     */
    @GetMapping("getTradeByParams")
    public CommonResult getTradeByParams(@RequestParam Map<String, Object> params) {
        TradeEntity trade = tradeService.getTradeByParams(params);
        return CommonResult.success(trade);
    }

    /**
     * 支付页面交易信息
     * @param params 参数
     * @return
     */
    @GetMapping("getTradeDetailByParams")
    public CommonResult getTradeDetailByParams(@RequestParam Map<String, Object> params) {
        TradeEntity trade = tradeService.getTradeDetailByParams(params);
        return CommonResult.success(trade);
    }

    /**
     * 支付成功后修改状态
     * @param code 交易号
     * @return
     */
    @PostMapping("updateTradeStatus")
    public CommonResult updateTradeStatus(@RequestParam("code") String code,
                                          @RequestParam("tradeStatus") String tradeStatus) {
        return tradeService.updateTradeStatus(code, tradeStatus) > 0 ? CommonResult.success() : CommonResult.fail();
    }
}
