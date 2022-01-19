package com.mall.admin.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mall.admin.entity.RegionEntity;
import com.mall.admin.enums.RegionTypeEnum;
import com.mall.admin.service.RegionService;
import com.mall.common.base.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 区域管理
 * @Date 2021/8/14 23:06
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "logistics/region")
public class RegionController {

    @Autowired
    private RegionService regionService;

    /**
     * 查询区域列表
     * @param params
     * @return
     */
    @GetMapping("list")
    public CommonResult getRegionList(@RequestParam Map<String, Object> params) {
        return CommonResult.success(regionService.queryRegionList(params));
    }

    /**
     * 根据id查询区域
     * @param id
     * @return
     */
    @GetMapping("getRegionById")
    public CommonResult getRegionById(@RequestParam Long id) {
        return CommonResult.success(regionService.getRegionById(id));
    }

    /**
     * 根据regions查询省、市、区县信息（如：安徽省 淮南市 寿县）
     * @param regions
     * @return
     */
    @GetMapping("getRegionsNameByRegions")
    public CommonResult getRegionsNameByRegions(String regions) {
        return CommonResult.success(regionService.getRegionsNameByRegions(regions));
    }

    /**
     * 新增地区
     * @param regionEntity
     * @return
     */
    @PostMapping("save")
    public CommonResult save(@RequestBody RegionEntity regionEntity) {
        int num = regionService.saveRegion(regionEntity);
        return num > 0 ? CommonResult.success() : CommonResult.fail();
    }

    /**
     * 修改地区
     * @param regionEntity
     * @return
     */
    @PutMapping("update")
    public CommonResult update(@RequestBody RegionEntity regionEntity) {
        int num = regionService.updateRegion(regionEntity);
        return num > 0 ? CommonResult.success() : CommonResult.fail();
    }

    /**
     * 删除地区（会删除子地区）
     * @param id
     * @return
     */
    @DeleteMapping("delete")
    public CommonResult delete(@RequestParam Long id) {
        int num = regionService.deleteRegion(id);
        return num > 0 ? CommonResult.success() : CommonResult.fail();
    }


    /**
     * 初始化中国区域数据
     * @param args
     */
    @GetMapping("init")
    public void init(String[] args) throws IOException {
        String filePath = "C:\\Users\\15221\\Desktop\\china_region.json";
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
            String provinceCode = String.valueOf(provinceObject.get("id"));
            String provinceName = String.valueOf(provinceObject.get("label"));
            //省
            RegionEntity province = new RegionEntity();
            province.setName(provinceName);
            province.setCode(provinceCode);
            province.setRegionType(RegionTypeEnum.province);
            province.setParentId(0L);
            regionService.save(province);

            JSONArray cityList = provinceObject.getJSONArray("children");
            if(cityList !=null && cityList.size()>0){
                for (int j=0; j<cityList.size(); j++){
                    JSONObject cityObject = cityList.getJSONObject(j);
                    String cityCode = String.valueOf(cityObject.get("id"));
                    String cityName = String.valueOf(cityObject.get("label"));
                    //市
                    RegionEntity city = new RegionEntity();
                    city.setName(cityName);
                    city.setCode(cityCode);
                    city.setRegionType(RegionTypeEnum.city);
                    city.setParentId(province.getId());
                    regionService.save(city);

                    JSONArray areaList = cityObject.getJSONArray("children");
                    if (areaList != null && areaList.size()>0){
                        for (int k=0; k<areaList.size(); k++){
                            JSONObject areaObject = areaList.getJSONObject(k);
                            String areaCode = String.valueOf(areaObject.get("id"));
                            String areaName = String.valueOf(areaObject.get("label"));
                            //区县
                            RegionEntity area = new RegionEntity();
                            area.setName(areaName);
                            area.setCode(areaCode);
                            area.setRegionType(RegionTypeEnum.area);
                            area.setParentId(city.getId());
                            regionService.save(area);
                        }
                    }
                }
            }
        }

        System.out.println("初始化完成 ...");
    }
}
