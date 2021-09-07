package com.mall.admin.controller;

import com.mall.admin.entity.LogisticsCompanyEntity;
import com.mall.admin.service.LogisticsCompanyService;
import com.mall.common.base.CommonResult;
import com.mall.common.base.enums.ResultCodeEnum;
import com.mall.common.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 物流公司
 * @Date 2021/9/7 21:35
 * @Version 1.0
 */
@RestController
@RequestMapping("logistics/company")
public class LogisticsCompanyController {

    @Autowired
    private LogisticsCompanyService logisticsCompanyService;

    /**
     * 分页查询物流公司列表
     */
    @GetMapping("/list")
    public CommonResult list(@RequestParam Map<String, Object> params){
        PageUtil page = logisticsCompanyService.getByPage(params);
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), page);
    }

    /**
     * 所有物流公司列表（不分页）
     */
    @GetMapping("/listAll")
    public CommonResult listAll(@RequestParam Map<String, Object> params){
        List<LogisticsCompanyEntity> logisticsCompanyEntity = logisticsCompanyService.getByParams(params);
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), logisticsCompanyEntity);
    }

    /**
     * 保存物流公司
     */
    @PostMapping("/save")
    public CommonResult save(@RequestBody LogisticsCompanyEntity logisticsCompanyEntity){
        int num = logisticsCompanyService.saveLogisticsCompany(logisticsCompanyEntity);
        if (num == -1){
            return CommonResult.fail(ResultCodeEnum.LOGISTICS_COMPANY_EXIST.getCode(),ResultCodeEnum.LOGISTICS_COMPANY_EXIST.getMessage());
        }
        return CommonResult.success();
    }

    /**
     * 修改物流公司
     */
    @PutMapping("/update")
    public CommonResult update(@RequestBody LogisticsCompanyEntity logisticsCompanyEntity){
        int num = logisticsCompanyService.updateLogisticsCompany(logisticsCompanyEntity);
        if (num == -1){
            return CommonResult.fail(ResultCodeEnum.LOGISTICS_COMPANY_EXIST.getCode(),ResultCodeEnum.LOGISTICS_COMPANY_EXIST.getMessage());
        }
        return CommonResult.success();
    }

    /**
     * 根据主键id获取物流公司
     * @param id
     * @return
     */
    @GetMapping("/getLogisticsCompanyById")
    public CommonResult getLogisticsCompanyById(@RequestParam long id) {
        LogisticsCompanyEntity logisticsCompanyEntity = logisticsCompanyService.getLogisticsCompanyById(id);
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), logisticsCompanyEntity);
    }

    /**
     * 删除物流公司
     */
    @DeleteMapping("/delete")
    public CommonResult delete(@RequestBody Long[] companyIds){
        logisticsCompanyService.deleteBatch(companyIds);
        return CommonResult.success();
    }
}
