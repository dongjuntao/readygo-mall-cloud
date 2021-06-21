package com.mall.brand.controller;

import com.mall.brand.entity.BrandEntity;
import com.mall.brand.service.BrandService;
import com.mall.common.base.CommonResult;
import com.mall.common.base.enums.ResultCodeEnum;
import com.mall.common.file.util.QCloudCosUtils;
import com.mall.common.redis.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 品牌Controller
 * @Date 2021/6/16 10:09
 * @Version 1.0
 */
@RestController
@RequestMapping("brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @Autowired
    private QCloudCosUtils qCloudCosUtils;

    /**
     * 新增品牌
     * @param brandEntity
     * @return
     */
    @PostMapping("save")
    public CommonResult save(@RequestBody BrandEntity brandEntity) {
        brandEntity.setCreateTime(new Date());
        brandEntity.setEnable(true);
        brandEntity.setOrderNum(0);
        brandService.saveOrUpdate(brandEntity);
        return CommonResult.success();
    }

    /**
     * 修改品牌
     */
    @PutMapping("/update")
    public CommonResult update(@RequestBody BrandEntity brandEntity){
        brandEntity.setUpdateTime(new Date());
        brandService.saveOrUpdate(brandEntity);
        return CommonResult.success();
    }

    /**
     * 品牌列表
     */
    @GetMapping("/list")
    public CommonResult list(@RequestParam Map<String, Object> params){
        PageUtil page = brandService.queryPage(params);
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), page);
    }

    /**
     * 根据主键id获取品牌
     * @param id
     * @return
     */
    @GetMapping("/getBrandById")
    public CommonResult getBrandById(@RequestParam long id) {
        BrandEntity brandEntity = brandService.getById(id);
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), brandEntity);
    }

    /**
     * 删除品牌
     */
    @DeleteMapping("/delete")
    public CommonResult delete(@RequestBody Long[] ids){
        //删除文件服务器中的图片
        for (Long id : ids) {
            BrandEntity entity = brandService.getById(id);
            qCloudCosUtils.delete(entity.getLogo());
        }
        brandService.removeByIds(Arrays.asList(ids));
        return CommonResult.success();
    }
}
