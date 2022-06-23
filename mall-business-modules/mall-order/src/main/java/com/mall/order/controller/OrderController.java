package com.mall.order.controller;

import com.mall.admin.api.feign.front.FrontPayTypeService;
import com.mall.common.base.CommonResult;
import com.mall.order.entity.TradeEntity;
import com.mall.order.service.OrderService;
import com.mall.order.service.TradeService;
import com.mall.order.vo.PayTypeVO;
import com.mall.order.vo.TradeDetailVO;
import com.mall.order.vo.TradeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @Author DongJunTao
 * @Description 订单controller
 * @Date 2022/6/16 19:51
 * @Version 1.0
 */
@RestController
@RequestMapping( "trade")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private TradeService tradeService;

    @Autowired
    private FrontPayTypeService frontPayTypeService;
    /**
     * 创建交易（以及订单和子订单）
     * @param tradeDetailVO 订单信息
     * @return
     */
    @PostMapping("createTrade")
    public CommonResult createTrade(@RequestBody TradeDetailVO tradeDetailVO) {
        TradeEntity tradeEntity = orderService.createTrade(tradeDetailVO);
        return CommonResult.success(tradeEntity);
    }

    /**
     * 交易信息
     * @param params 参数
     * @return
     */
    @GetMapping("tradeInfo")
    public CommonResult tradeInfo(@RequestParam Map<String, Object> params) {
        TradeEntity trade = tradeService.getTradeByParams(params);
        TradeVO tradeVO = new TradeVO();
        tradeVO.setTradeTime(trade.getTradeTime());
        tradeVO.setFinalPrice(trade.getFinalPrice());
        tradeVO.setOvertime(new Date(trade.getTradeTime().getTime() + (30*60*1000))); //交易过期时间（交易后30分钟内）
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
            tradeVO.setPayTypeList(payTypeList);
        }
        return CommonResult.success(tradeVO);
    }
}
