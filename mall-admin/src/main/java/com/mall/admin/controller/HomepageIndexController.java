package com.mall.admin.controller;

import com.mall.admin.entity.HomepageNavbarEntity;
import com.mall.admin.entity.HomepagePlateEntity;
import com.mall.admin.service.HomepageNavbarService;
import com.mall.admin.service.HomepagePlateService;
import com.mall.common.base.CommonResult;
import com.mall.common.base.enums.ResultCodeEnum;
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
    /**
     * 首页数据
     * @return
     */
    @GetMapping("/data")
    public CommonResult indexData() {
        Map<String, Object> indexDataMap = new HashMap<>();
        //首页导航栏信息
        Map<String,Object> paramsMap = new HashMap<>();
        List<HomepageNavbarEntity> homepageNavbarList = homepageNavbarService.getByParams(paramsMap);
        //首页板块信息
        List<HomepagePlateEntity> homepagePlateList = homepagePlateService.getByParams(paramsMap);

        //====组装数据
        List<Object> dataList = new ArrayList<>();
        //首页导航
        Map<String,Object> navbarMap = new HashMap<>();
        navbarMap.put("type","navBar");
        navbarMap.put("list", homepageNavbarList);
        dataList.add(navbarMap);
        //首页板块
        for (int i=0; i<homepagePlateList.size(); i++) {
            HomepagePlateEntity plateEntity = homepagePlateList.get(i);
            Map<String,Object> plateMap = new HashMap<>();
            plateMap.put("type", plateEntity.getType());
            plateMap.put("name", plateEntity.getName());
            List<Object> goodsList = plateEntity.getList();
            Map<String,Object> listMap = new HashMap<>();
            listMap.put("list", goodsList);
            plateMap.put("options", listMap);
            dataList.add(plateMap);
        }


        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), dataList);
    }
}
