package com.mall.admin.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mall.admin.entity.ChinaRegionEntity;
import com.mall.admin.enums.RegionTypeEnum;
import com.mall.admin.service.ChinaRegionService;
import com.mall.common.base.CommonResult;
import com.sun.istack.internal.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 中国区域
 * @Date 2021/8/14 23:06
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "system/region")
public class ChinaRegionController {

    @Autowired
    private ChinaRegionService chinaRegionService;

    /**
     * 查询区域列表
     * @param params
     * @return
     */
    @GetMapping("list")
    public CommonResult getChinaRegionList(@RequestParam Map<String, Object> params) {
        return CommonResult.success(chinaRegionService.queryChinaRegionList(params));
    }

    /**
     * 根据id查询区域
     * @param id
     * @return
     */
    @GetMapping("getChinaRegionById")
    public CommonResult getChinaRegionById(@RequestParam Long id) {
        return CommonResult.success(chinaRegionService.getChinaRegionById(id));
    }

    /**
     * 根据regions查询省、市、区县信息（如：安徽省 淮南市 寿县）
     * @param regions
     * @return
     */
    @GetMapping("getRegionsNameByRegions")
    public CommonResult getRegionsNameByRegions(String regions) {
        return CommonResult.success(chinaRegionService.getRegionsNameByRegions(regions));
    }


    /**
     * 初始化中国区域数据
     * @param args
     */
    @GetMapping("init")
    public void init(String[] args) throws IOException {
        String filePath = "C:\\Users\\15221164409\\Desktop\\china_region.json";
        File file = new File(filePath);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        StringBuffer stringBuffer = new StringBuffer();
        String result;
        while ((result = bufferedReader.readLine()) != null) {
            stringBuffer.append(result);
        }
        System.out.println("s == "+stringBuffer);

        JSONArray array = JSONArray.parseArray(stringBuffer.toString());
        //遍历List, 插入数据库
        for (int i=0; i<array.size(); i++) {
            JSONObject provinceObject = array.getJSONObject(i);
            String provinceCode = String.valueOf(provinceObject.get("code"));
            String provinceName = String.valueOf(provinceObject.get("name"));
            //省
            ChinaRegionEntity province = new ChinaRegionEntity();
            province.setName(provinceName);
            province.setCode(provinceCode);
            province.setRegionType(RegionTypeEnum.province);
            province.setParentId(0L);
            chinaRegionService.save(province);

            JSONArray cityList = provinceObject.getJSONArray("cityList");
            for (int j=0; j<cityList.size(); j++){
                JSONObject cityObject = cityList.getJSONObject(j);
                String cityCode = String.valueOf(cityObject.get("code"));
                String cityName = String.valueOf(cityObject.get("name"));
                //市
                ChinaRegionEntity city = new ChinaRegionEntity();
                city.setName(cityName);
                city.setCode(cityCode);
                city.setRegionType(RegionTypeEnum.city);
                city.setParentId(province.getId());
                chinaRegionService.save(city);

                JSONArray areaList = cityObject.getJSONArray("areaList");
                for (int k=0; k<areaList.size(); k++){
                    JSONObject areaObject = areaList.getJSONObject(k);
                    String areaCode = String.valueOf(areaObject.get("code"));
                    String areaName = String.valueOf(areaObject.get("name"));
                    //区县
                    ChinaRegionEntity area = new ChinaRegionEntity();
                    area.setName(areaName);
                    area.setCode(areaCode);
                    area.setRegionType(RegionTypeEnum.area);
                    area.setParentId(city.getId());
                    chinaRegionService.save(area);
                }
            }
        }

        System.out.println("初始化完成 ...");
    }
}
