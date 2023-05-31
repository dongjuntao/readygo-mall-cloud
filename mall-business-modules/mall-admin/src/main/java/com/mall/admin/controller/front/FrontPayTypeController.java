package com.mall.admin.controller.front;

import com.mall.admin.entity.PayTypeEntity;
import com.mall.admin.service.PayTypeService;
import com.mall.common.base.CommonResult;
import com.mall.common.base.enums.ResultCodeEnum;
import com.mall.common.base.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 支付管理（支付方式）
 * @Date 2022/6/19 14:46
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "front/payType")
public class FrontPayTypeController {

    @Autowired
    private PayTypeService payTypeService;

    /**
     * 查询支付方式（不分页）
     */
    @GetMapping("listAll")
    public CommonResult listAll(@RequestParam(value = "name", required = false) String name,
                                @RequestParam(value = "enable", required = false) Boolean enable){
        List<PayTypeEntity> payTypeList = payTypeService.getPayTypeList(name, enable);
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), payTypeList);
    }
}
