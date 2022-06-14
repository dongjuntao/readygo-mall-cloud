package com.mall.admin.controller.front;

import com.mall.admin.entity.FreightTemplateEntity;
import com.mall.admin.service.FreightTemplateService;
import com.mall.common.base.CommonResult;
import com.mall.common.base.enums.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author DongJunTao
 * @Description 运费模板，供前台使用
 * @Date 2022/6/8 21:25
 * @Version 1.0
 */
@RestController
@RequestMapping("front/store/freightTemplate")
public class FrontFreightTemplateController {

    @Autowired
    private FreightTemplateService freightTemplateService;

    /**
     * 根据主键id获取运费模板
     * @param id
     * @return
     */
    @GetMapping("getFreightTemplateById")
    public CommonResult getShippingInfoById(@RequestParam long id) {
        FreightTemplateEntity freightTemplateEntity = freightTemplateService.getFreightTemplateById(id);
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), freightTemplateEntity);
    }
}
