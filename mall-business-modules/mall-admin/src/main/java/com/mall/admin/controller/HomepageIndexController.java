package com.mall.admin.controller;

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
import com.mall.goods.api.FeignGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("homepage/index")
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
    private FeignGoodsService feignGoodsService;

    /**
     * 首页数据
     * @return
     */
    @GetMapping("/data")
    public CommonResult indexData() {

        Map<String, Object> indexDataMap = new HashMap<>();

        //首页导航栏信息
        Map<String,Object> navbarParamsMap = new HashMap<>();
        List<HomepageNavbarEntity> homepageNavbarList = homepageNavbarService.getByParams(navbarParamsMap);

        //首页轮播图
        Map<String,Object> carouselParamsMap = new HashMap<>();
        List<HomepageCarouselEntity> homepageCarouselList = homepageCarouselService.getByParams(carouselParamsMap);

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
                CommonResult result = feignGoodsService.info(goodsId);
                goodsList.add(result.getData());
            }
            homepagePlateList.get(i).setList(goodsList);
        }

        //===========================================组装数据================================================
        List<Object> dataList = new ArrayList<>();

        //首页导航
        Map<String,Object> navbarMap = new HashMap<>();
        navbarMap.put("type","navBar");
        navbarMap.put("list", homepageNavbarList);
        dataList.add(navbarMap);

        //首页轮播图
        Map<String,Object> carouselMap = new HashMap<>();
        Map<String,Object> options = new HashMap<>();
        carouselMap.put("type","carousel");
        carouselMap.put("name","图片轮播");
        List<Object> carouselInfoList = new ArrayList<>();
        for (int i=0; i<homepageCarouselList.size(); i++) {
            Map<String, String> carouselInfo = new HashMap<>();
            carouselInfo.put("img", homepageCarouselList.get(i).getUrl());
            carouselInfoList.add(carouselInfo);
        }
        options.put("list", carouselInfoList);
        carouselMap.put("options", options);
        carouselMap.put("key","carousel");
        dataList.add(carouselMap);

        //首页板块（包括关联的商品信息）
        for (int i=0; i<homepagePlateList.size(); i++) {
            HomepagePlateEntity plateEntity = homepagePlateList.get(i);
            Map<String,Object> plateMap = new HashMap<>();
            plateMap.put("type", plateEntity.getType());
            plateMap.put("name", plateEntity.getName());
            plateMap.put("secondName", plateEntity.getSecondName());
            plateMap.put("bgColor",plateEntity.getBgColor());
            Map<String,Object> listMap = new HashMap<>();
            listMap.put("list", plateEntity.getList());
            plateMap.put("options", listMap);
            plateMap.put("key","plate"+plateEntity.getName());
            dataList.add(plateMap);
        }

        indexDataMap.put("list", dataList);

        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), indexDataMap);
    }
}
