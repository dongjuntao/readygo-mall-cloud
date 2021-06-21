package com.mall.brand.controller;

import com.mall.brand.entity.BrandCategoryEntity;
import com.mall.brand.service.BrandCategoryService;
import com.mall.common.base.CommonResult;
import com.mall.common.base.enums.ResultCodeEnum;
import com.mall.common.redis.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 品牌分类controller
 * @Date 2021/6/16 10:44
 * @Version 1.0
 */
@RestController
@RequestMapping("brandCategory")
public class BrandCategoryController {

    @Autowired
    private BrandCategoryService brandCategoryService;

    /**
     * 新增品牌分类
     * @param brandCategoryEntity
     * @return
     */
    @PostMapping("save")
    public CommonResult save(@RequestBody BrandCategoryEntity brandCategoryEntity) {
        brandCategoryEntity.setCreateTime(new Date());
        brandCategoryService.saveOrUpdate(brandCategoryEntity);
        return CommonResult.success();
    }

    /**
     * 修改品牌分类
     */
    @PutMapping("/update")
    public CommonResult update(@RequestBody  BrandCategoryEntity brandCategoryEntity){
        brandCategoryEntity.setUpdateTime(new Date());
        brandCategoryService.saveOrUpdate(brandCategoryEntity);
        return CommonResult.success();
    }

    /**
     * 品牌分类列表
     */
    @GetMapping("/list")
    public CommonResult list(@RequestParam Map<String, Object> params){
        PageUtil page = brandCategoryService.queryPage(params);
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), page);
    }

    /**
     * 品牌分类列表
     */
    @GetMapping("/listAll")
    public CommonResult listAll(){
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(),
                brandCategoryService.queryAll());
    }

    /**
     * 根据主键id获取品牌分类
     * @param id
     * @return
     */
    @GetMapping("/getBrandCategoryById")
    public CommonResult getBrandCategoryById(@RequestParam long id) {
        BrandCategoryEntity brandCategoryEntity = brandCategoryService.getById(id);
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), brandCategoryEntity);
    }

    /**
     * 删除品牌分类
     */
    @DeleteMapping("/delete")
    public CommonResult delete(@RequestBody Long[] userIds){
        brandCategoryService.removeByIds(Arrays.asList(userIds));
        return CommonResult.success();
    }
}
