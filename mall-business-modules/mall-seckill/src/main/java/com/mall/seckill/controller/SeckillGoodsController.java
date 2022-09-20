package com.mall.seckill.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mall.common.base.CommonResult;
import com.mall.common.base.enums.ResultCodeEnum;
import com.mall.goods.api.FeignGoodsService;
import com.mall.seckill.entity.SeckillGoodsSkuEntity;
import com.mall.seckill.service.SeckillConfigService;
import com.mall.seckill.service.SeckillGoodsSkuService;
import com.mall.seckill.vo.GoodsSkuVO;
import com.mall.seckill.vo.SeckillGoodsSkuVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 秒杀商品controller
 * @Date 2022/1/7 15:37
 * @Version 1.0
 */
@RestController
@RequestMapping("seckillGoods")
public class SeckillGoodsController {

    @Autowired
    private SeckillGoodsSkuService seckillGoodsSkuService;

    @Autowired
    private FeignGoodsService feignGoodsService;

    @Autowired
    private SeckillConfigService seckillConfigService;

    /**
     * 秒杀商品详细信息（包含商品sku信息）
     */
    @GetMapping("/sku/list")
    public CommonResult list(@RequestParam Map<String, Object> params){
        Long goodsId = params.get("goodsId") == null ? null: Long.valueOf((params.get("goodsId").toString()));
        List<GoodsSkuVO> goodsSkuList = new ArrayList<>();
        CommonResult goodsResult = feignGoodsService.info(goodsId);
        if (goodsResult != null && "200".equals(goodsResult.getCode())) {
            JSONObject goodsObject = JSON.parseObject(JSON.toJSONString(goodsResult.getData()));
            goodsSkuList = goodsObject.getJSONArray("goodsSkuList").toJavaList(GoodsSkuVO.class);
            List<Long> skuIds = new ArrayList<>();
            goodsSkuList.stream().forEach(goodsSkuVO -> skuIds.add(goodsSkuVO.getGoodsId()));
            List<SeckillGoodsSkuEntity> seckillGoodsSkuList = seckillGoodsSkuService.getSeckillGoodsSkuListByIds(skuIds);
            if (CollectionUtils.isEmpty(seckillGoodsSkuList)) {
                for (SeckillGoodsSkuEntity seckillGoodsSku : seckillGoodsSkuList) {
                    for (GoodsSkuVO goodsSkuVO : goodsSkuList) {
                        if (seckillGoodsSku.getGoodsSkuId().equals(goodsSkuVO.getId())) {
                            SeckillGoodsSkuVO seckillGoodsSkuVO = new SeckillGoodsSkuVO();
                            BeanUtils.copyProperties(seckillGoodsSku, seckillGoodsSkuVO);
                            goodsSkuVO.setSeckillGoodsSkuVO(seckillGoodsSkuVO);
                        }
                    }
                }
            }
        }
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), goodsSkuList);
    }


    /**
     * 获取当天所有批次的商品
     */
    @GetMapping("/batch")
    public CommonResult seckillGoodsBatch(@RequestParam(value = "dateTime",required = false) String dateTime) throws ParseException {
        LocalDateTime localDateTime;
        if (StringUtils.isEmpty(dateTime)) {
            localDateTime = LocalDateTime.now();//当前时间
        }else {
            localDateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        String currentBatch = getBatchByCurrentTime(localDateTime);
        String startDateTime = currentBatch.split(",")[0]; //起始日期时间
        String endDateTime = currentBatch.split(",")[1]; //结束日期时间
        Map<String, Object> params = new HashMap<>();
        params.put("seckillDate", startDateTime.split(" ")[0]);
        params.put("seckillStartTime", startDateTime.split(" ")[1]);
        params.put("seckillEndTime", endDateTime.split(" ")[1]);
        return CommonResult.success(seckillConfigService.getByParams(params)) ;
    }

    /**
     * 根据当前时间获取当前批次
     */
    private String getBatchByCurrentTime(LocalDateTime localDateTime) {
        int hour = localDateTime.getHour(); //当前小时
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = dateTimeFormatter.format(localDateTime);
        String currentBatch = "";
        switch (hour) {
            case 0:
            case 1:
                currentBatch = date+" 00:00:00" + "," +date+" 02:00:00"; break;
            case 2:
            case 3:
                currentBatch = date+" 02:00:00" + "," +date+" 04:00:00"; break;
            case 4:
            case 5:
                currentBatch = date+" 04:00:00" + "," +date+" 06:00:00"; break;
            case 6:
            case 7:
                currentBatch = date+" 06:00:00" + "," +date+" 08:00:00"; break;
            case 8:
            case 9:
                currentBatch = date+" 08:00:00" + "," +date+" 10:00:00"; break;
            case 10:
            case 11:
                currentBatch = date+" 10:00:00" + "," +date+" 12:00:00"; break;
            case 12:
            case 13:
                currentBatch = date+" 12:00:00" + "," +date+" 14:00:00"; break;
            case 14:
            case 15:
                currentBatch = date+" 14:00:00" + "," +date+" 16:00:00"; break;
            case 16:
            case 17:
                currentBatch = date+" 16:00:00" + "," +date+" 18:00:00"; break;
            case 18:
            case 19:
                currentBatch = date+" 18:00:00" + "," +date+" 20:00:00"; break;
            case 20:
            case 21:
                currentBatch = date+" 20:00:00" + "," +date+" 22:00:00"; break;
            case 22:
            case 23:
                currentBatch = date+" 22:00:00" + "," +date+" 23:59:59"; break;
            default: currentBatch = null;
        }
        return currentBatch;
    }

}
