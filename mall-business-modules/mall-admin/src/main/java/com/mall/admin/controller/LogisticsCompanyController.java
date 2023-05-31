package com.mall.admin.controller;

import com.mall.admin.entity.LogisticsCompanyEntity;
import com.mall.admin.service.LogisticsCompanyService;
import com.mall.common.base.CommonResult;
import com.mall.common.base.enums.ResultCodeEnum;
import com.mall.common.base.utils.PageUtil;
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
     * 分页查询所有物流公司
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @param name 物流公司名称
     * @param abbreviation 物流公司简称
     * @param code 物流公司编码
     * @return
     */
    @GetMapping("/list")
    public CommonResult list(@RequestParam(value = "pageNum", required = false) Integer pageNum,
                             @RequestParam(value = "pageSize", required = false) Integer pageSize,
                             @RequestParam(value = "name", required = false) String name,
                             @RequestParam(value = "abbreviation", required = false) String abbreviation,
                             @RequestParam(value = "code", required = false)  String code){
        PageUtil page = logisticsCompanyService.getByPage(pageNum,pageSize,name,abbreviation,code);
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), page);
    }

    /**
     * 根据条件查询所有物流公司（不分页）
     * @param name 物流公司名称
     * @param abbreviation 物流公司简称
     * @param code 物流公司编码
     * @return
     */
    @GetMapping("/listAll")
    public CommonResult listAll(@RequestParam(value = "name", required = false) String name,
                                @RequestParam(value = "abbreviation", required = false) String abbreviation,
                                @RequestParam(value = "code", required = false)  String code){
        List<LogisticsCompanyEntity> logisticsCompanyEntity = logisticsCompanyService.getByParams(name, abbreviation, code);
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), logisticsCompanyEntity);
    }

    /**
     *分页查询所有物流公司（与express_setting联合查询）
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @param name 物流公司名称
     * @param abbreviation 物流公司简称
     * @param code 物流公司编码
     * @param adminUserId 所属商家id
     * @return
     */
    @GetMapping("/listWithExpressSetting")
    public CommonResult listWithExpressSetting(@RequestParam(value = "pageNum", required = false) Integer pageNum,
                                               @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                               @RequestParam(value = "name", required = false) String name,
                                               @RequestParam(value = "abbreviation", required = false) String abbreviation,
                                               @RequestParam(value = "code", required = false) String code,
                                               @RequestParam(value = "adminUserId", required = false) Long adminUserId){
        PageUtil page = logisticsCompanyService.getWithExpressSettingByPage(pageNum, pageSize,name,abbreviation,code,adminUserId);
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), page);
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
