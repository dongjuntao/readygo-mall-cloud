package com.mall.admin.controller;

import com.mall.admin.entity.FreightTemplateEntity;
import com.mall.admin.service.FreightTemplateService;
import com.mall.common.base.CommonResult;
import com.mall.common.base.enums.ResultCodeEnum;
import com.mall.common.base.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 运费模板
 * @Date 2021/9/17 22:54
 * @Version 1.0
 */
@RestController
@RequestMapping("store/freightTemplate")
public class FreightTemplateController {

    @Autowired
    private FreightTemplateService freightTemplateService;

    /**
     * 分页查询运费模板列表
     */
    @GetMapping("/list")
    public CommonResult list(@RequestParam Map<String, Object> params){
        PageUtil page = freightTemplateService.getByPage(params);
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), page);
    }

    /**
     * 查询所有运费模板列表（不分页）
     */
    @GetMapping("/listAll")
    public CommonResult listAll(@RequestParam Map<String, Object> params){
        List<FreightTemplateEntity> freightTemplateEntityList = freightTemplateService.getByParams(params);
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), freightTemplateEntityList);
    }
    /**
     * 新增运费模板
     */
    @PostMapping("/save")
    public CommonResult save(@RequestBody FreightTemplateEntity freightTemplateEntity){
        int num = freightTemplateService.saveFreightTemplate(freightTemplateEntity);
        if (num < 0){
            return CommonResult.fail(ResultCodeEnum.CREATE_FAIL.getCode(),ResultCodeEnum.CREATE_FAIL.getMessage());
        }
        return CommonResult.success();
    }

    /**
     * 修改运费模板
     */
    @PutMapping("/update")
    public CommonResult update(@RequestBody FreightTemplateEntity freightTemplateEntity){
        int num = freightTemplateService.updateFreightTemplate(freightTemplateEntity);
        if (num < 0){
            return CommonResult.fail(ResultCodeEnum.UPDATE_FAIL.getCode(),ResultCodeEnum.UPDATE_FAIL.getMessage());
        }
        return CommonResult.success();
    }

    /**
     * 根据主键id获取运费模板
     * @param id
     * @return
     */
    @GetMapping("/getFreightTemplateById")
    public CommonResult getShippingInfoById(@RequestParam long id) {
        FreightTemplateEntity freightTemplateEntity = freightTemplateService.getFreightTemplateById(id);
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), freightTemplateEntity);
    }


    /**
     * 设为默认 / 取消默认
     * @param id
     * @return
     */
    @PutMapping("updateIsDefault/{id}")
    public CommonResult updateIsDefault(@PathVariable("id") Long id, @RequestParam("isDefault") Boolean isDefault) {
        freightTemplateService.updateIsDefault(id, isDefault);
        return CommonResult.success();
    }

    /**
     * 删除运费模板
     */
    @DeleteMapping("/delete")
    public CommonResult delete(@RequestBody Long[] freightTemplateIds){
        freightTemplateService.deleteBatch(freightTemplateIds);
        return CommonResult.success();
    }
}
