package com.mall.admin.controller.front;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.mall.admin.entity.HomepageCarouselEntity;
import com.mall.admin.entity.HomepageNavbarEntity;
import com.mall.admin.entity.HomepagePlateEntity;
import com.mall.admin.entity.HomepagePlateGoodsRelatedEntity;
import com.mall.admin.service.HomepageCarouselService;
import com.mall.admin.service.HomepageNavbarService;
import com.mall.admin.service.HomepagePlateGoodsRelatedService;
import com.mall.admin.service.HomepagePlateService;
import com.mall.common.base.CommonResult;
import com.mall.common.base.enums.ResultCodeEnum;
import com.mall.goods.api.FeignFrontGoodsService;
import com.mall.seckill.api.feign.FeignSeckillGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 商城首页数据处理，用于PC端商城首页数据展示
 * @Date 2022/2/12 22:54
 * @Version 1.0
 */
@RestController
@RequestMapping("homepage/data")
public class HomepageIndexController {

    @Autowired
    private HomepageNavbarService homepageNavbarService;

    @Autowired
    private HomepagePlateService homepagePlateService;

    @Autowired
    private HomepageCarouselService homepageCarouselService;

    @Autowired
    private HomepagePlateGoodsRelatedService homepagePlateGoodsRelatedService;

    @Autowired
    private FeignFrontGoodsService feignFrontGoodsService;

    @Autowired
    private FeignSeckillGoodsService feignSeckillGoodsService;

    /**
     * 导航栏数据
     * @return
     */
    @GetMapping("navbarData")
    public CommonResult navbarData() {
        //首页导航栏信息
        Map<String,Object> navbarParamsMap = new HashMap<>();
        List<HomepageNavbarEntity> homepageNavbarList = homepageNavbarService.getByParams(navbarParamsMap);
        List<Object> dataList = new ArrayList<>();
        //首页导航栏数据组装
        Map<String,Object> navbarMap = new HashMap<>();
        navbarMap.put("name", "导航栏");
        navbarMap.put("type","navBar");
        navbarMap.put("data", homepageNavbarList);
        dataList.add(navbarMap);
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), dataList);
    }


    /**
     * 轮播图数据
     * @return
     */
    @GetMapping("carouselData")
    public CommonResult carouselData() {
        //首页轮播图
        Map<String,Object> carouselParamsMap = new HashMap<>();
        List<HomepageCarouselEntity> homepageCarouselList = homepageCarouselService.getByParams(carouselParamsMap);

        List<Object> dataList = new ArrayList<>();

        //首页轮播图
        Map<String,Object> carouselMap = new HashMap<>();
        carouselMap.put("name","图片轮播");
        carouselMap.put("type","carousel");

        List<Object> carouselInfoList = new ArrayList<>();
        for (int i=0; i<homepageCarouselList.size(); i++) {
            Map<String, Object> carouselInfo = new HashMap<>();
            carouselInfo.put("img", homepageCarouselList.get(i).getUrl());
            carouselInfo.put("goodsId", homepageCarouselList.get(i).getGoodsId());
            carouselInfoList.add(carouselInfo);
        }
        carouselMap.put("data", carouselInfoList);
        carouselMap.put("key","carousel");
        dataList.add(carouselMap);
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), dataList);
    }


    /**
     * 板块数据
     * @return
     */
    @GetMapping("plateData")
    public CommonResult plateData() {
        //首页板块信息（包括关联的商品信息）
        Map<String,Object> plateParamsMap = new HashMap<>();
        List<HomepagePlateEntity> homepagePlateList = homepagePlateService.getByParams(plateParamsMap);
        for (int i=0; i<homepagePlateList.size(); i++) {
            List<Object> goodsList = new ArrayList<>();
            Long plateId = homepagePlateList.get(i).getId();
            List<HomepagePlateGoodsRelatedEntity> plateGoodsRelatedList =
                    homepagePlateGoodsRelatedService.getHomepagePlateGoodsRelatedList(plateId);
            for (int j=0; j<plateGoodsRelatedList.size(); j++) {
                Long goodsId = plateGoodsRelatedList.get(j).getGoodsId();
                CommonResult result = feignFrontGoodsService.getGoodsById(goodsId);
                if ("200".equals(result.getCode()) && result.getData() != null) {
                    goodsList.add(result.getData());
                }
            }
            homepagePlateList.get(i).setList(goodsList);
        }

        List<Object> dataList = new ArrayList<>();
        //首页板块（包括关联的商品信息）
        for (int i=0; i<homepagePlateList.size(); i++) {
            HomepagePlateEntity plateEntity = homepagePlateList.get(i);
            Map<String,Object> plateMap = new HashMap<>();
            plateMap.put("type", plateEntity.getType());
            plateMap.put("name", plateEntity.getName());
            plateMap.put("secondName", plateEntity.getSecondName());
            plateMap.put("bgColor",plateEntity.getBgColor());
            plateMap.put("data", plateEntity.getList());
            plateMap.put("key","plate"+plateEntity.getName());
            dataList.add(plateMap);
        }
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), dataList);
    }

    /**
     * 获取当前及未来（共5个）时间批次
     * @return
     */
    @GetMapping("afterFiveBatch")
    public CommonResult afterFiveBatch() throws ParseException {

        LocalDateTime localDateTime = LocalDateTime.now();
        List<String> localDateTimeList = new ArrayList<>();
        int count = 0;
        while (count < 5) {
            String dateTime =  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(localDateTime);
            localDateTimeList.add(dateTime);
            count++;
            localDateTime = localDateTime.plusHours(2);
        }
        //秒杀数据组装
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), localDateTimeList);
    }

    /**
     * 首页秒杀数据
     * @return
     */
    @GetMapping("seckillData")
    public CommonResult seckillData(@RequestParam(value = "dateTime",required = false) String dateTime) throws ParseException {
        CommonResult seckillGoodsResult;
        LocalDateTime localDateTime;
        if (StringUtils.isEmpty(dateTime)) {
            seckillGoodsResult = feignSeckillGoodsService.seckillGoodsBatch(null);
            localDateTime = LocalDateTime.now();
        } else {
            seckillGoodsResult = feignSeckillGoodsService.seckillGoodsBatch(dateTime);
            localDateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }

        JSONArray jsonSeckillGoods = JSON.parseArray(JSON.toJSONString(seckillGoodsResult.getData()));
        List<Long> goodsIds = new ArrayList<>();
        jsonSeckillGoods.stream().forEach(array->goodsIds.add(JSON.parseObject(JSON.toJSONString(array)).getLong("goodsId")));
        //远程获取商品信息
        CommonResult goodsResult = feignFrontGoodsService.listByIds(goodsIds.stream().toArray(Long[]::new));
        JSONArray jsonGoods = JSON.parseArray(JSON.toJSONString(goodsResult.getData()));
        for (int i=0; i<jsonGoods.size(); i++) {
            for (int j=0; j<jsonSeckillGoods.size(); j++) {
                if (jsonGoods.getJSONObject(i).getLong("id").equals(jsonSeckillGoods.getJSONObject(j).getLong("goodsId"))) {
                    jsonGoods.getJSONObject(i).put("seckillGoodsInfo", jsonSeckillGoods.getJSONObject(j));
                }
            }
        }

        List<Object> dataList = new ArrayList<>();
        //秒杀数据组装
        Map<String,Object> seckillMap = new HashMap<>();
        seckillMap.put("name", "秒杀");
        seckillMap.put("type","seckill");
        seckillMap.put("data", jsonGoods);
        seckillMap.put("timeLine", localDateTime.getHour()%2==0 ? localDateTime.getHour() : localDateTime.getHour()-1);
        dataList.add(seckillMap);
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), dataList);
    }

}
