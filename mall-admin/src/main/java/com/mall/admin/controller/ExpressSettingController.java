package com.mall.admin.controller;

import com.mall.admin.entity.ExpressSettingEntity;
import com.mall.admin.entity.LogisticsCompanyEntity;
import com.mall.admin.service.ExpressSettingService;
import com.mall.common.base.CommonResult;
import com.mall.common.base.enums.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author DongJunTao
 * @Description 快递设置
 * @Date 2021/9/11 16:27
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "store/expressSetting")
public class ExpressSettingController {

    @Autowired
    private ExpressSettingService expressSettingService;

    /**
     * 启用（新增一条记录）
     */
    @PostMapping("/save")
    public CommonResult save(@RequestBody ExpressSettingEntity expressSettingEntity){
        int num = expressSettingService.saveExpressSetting(expressSettingEntity);
        if (num == 0){
            return CommonResult.fail(ResultCodeEnum.CREATE_FAIL.getCode(),ResultCodeEnum.CREATE_FAIL.getMessage());
        }
        return CommonResult.success();
    }


    /**
     * 禁用（删除一条记录）
     */
    @DeleteMapping("/delete")
    public CommonResult delete(@RequestParam("logisticsCompanyId") Long logisticsCompanyId,
                               @RequestParam("adminUserId") Long adminUserId){
        expressSettingService.delete(logisticsCompanyId,adminUserId);
        return CommonResult.success();
    }
}
