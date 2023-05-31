package com.mall.admin.controller;

import com.mall.admin.entity.AdminUserEntity;
import com.mall.admin.entity.PayTypeEntity;
import com.mall.admin.service.PayTypeService;
import com.mall.common.base.CommonResult;
import com.mall.common.base.enums.ResultCodeEnum;
import com.mall.common.base.utils.PageUtil;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 支付管理（支付方式）
 * @Date 2022/6/19 14:46
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "payType")
public class PayTypeController {

    @Autowired
    private PayTypeService payTypeService;

    /**
     * 分页查询支付方式
     */
    @GetMapping("list")
    public CommonResult list(@RequestParam(value = "pageNum", required = false) Integer pageNum,
                             @RequestParam(value = "pageSize", required = false) Integer pageSize,
                             @RequestParam(value = "name", required = false) String name,
                             @RequestParam(value = "enable", required = false) Boolean enable){
        PageUtil page = payTypeService.queryPage(pageNum, pageSize, name, enable);
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), page);
    }

    /**
     * 根据id查询支付方式
     */
    @GetMapping("getPayTypeById")
    public CommonResult getPayTypeById(@RequestParam("id") Long id){
        PayTypeEntity payType = payTypeService.getPayTypeById(id);
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), payType);
    }

    /**
     * 保存支付方式信息
     */
    @PostMapping("/save")
    public CommonResult save(@RequestBody PayTypeEntity payType){
        int num = payTypeService.savePayType(payType);
        return num > 0 ? CommonResult.success() : CommonResult.fail();
    }

    /**
     * 修改支付方式信息
     */
    @PutMapping("/update")
    public CommonResult update(@RequestBody PayTypeEntity payType){
        int num = payTypeService.updatePayType(payType);
        return num > 0 ? CommonResult.success() : CommonResult.fail();
    }

    /**
     * 删除支付方式信息
     */
    @DeleteMapping("/delete")
    public CommonResult delete(@RequestBody Long[] ids){
        payTypeService.deleteBatch(Arrays.asList(ids));
        return CommonResult.success();
    }

    /**
     * 启用 / 禁用
     */
    @PutMapping("/updateEnable")
    public CommonResult updateEnable(@RequestParam("id") Long id,
                                     @RequestParam("enable") Boolean enable){
        int num = payTypeService.updateEnable(id, enable);
        return num > 0 ? CommonResult.success() : CommonResult.fail();
    }
}
