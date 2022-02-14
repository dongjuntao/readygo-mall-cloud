package com.mall.admin.controller;

import com.mall.admin.entity.HomepagePlateEntity;
import com.mall.admin.service.HomepagePlateService;
import com.mall.common.base.CommonResult;
import com.mall.common.base.enums.ResultCodeEnum;
import com.mall.common.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 商城首页板块
 * @Date 2022/2/10 22:58
 * @Version 1.0
 */
@RestController
@RequestMapping("homepage/plate")
public class HomepagePlateController {


    @Autowired
    private HomepagePlateService homepagePlateService;

    /**
     * 分页查询商城首页板块列表
     */
    @GetMapping("/list")
    public CommonResult list(@RequestParam Map<String, Object> params){
        PageUtil page = homepagePlateService.getByPage(params);
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), page);
    }

    /**
     * 所有板块列表（不分页）
     */
    @GetMapping("/listAll")
    public CommonResult listAll(@RequestParam Map<String, Object> params){
        List<HomepagePlateEntity> homepagePlateEntityList = homepagePlateService.getByParams(params);
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), homepagePlateEntityList);
    }

    /**
     * 保存板块
     */
    @PostMapping("/save")
    public CommonResult save(@RequestBody HomepagePlateEntity homepagePlateEntity){
        int num = homepagePlateService.saveHomepagePlate(homepagePlateEntity);
        if (num == -1){
            return CommonResult.fail(ResultCodeEnum.LOGISTICS_COMPANY_EXIST.getCode(),ResultCodeEnum.LOGISTICS_COMPANY_EXIST.getMessage());
        }
        return CommonResult.success();
    }

    /**
     * 修改板块
     */
    @PutMapping("/update")
    public CommonResult update(@RequestBody HomepagePlateEntity homepagePlateEntity){
        int num = homepagePlateService.updateHomepagePlate(homepagePlateEntity);
        if (num == -1){
            return CommonResult.fail(ResultCodeEnum.LOGISTICS_COMPANY_EXIST.getCode(),ResultCodeEnum.LOGISTICS_COMPANY_EXIST.getMessage());
        }
        return CommonResult.success();
    }

    /**
     * 根据主键id获取板块
     * @param id
     * @return
     */
    @GetMapping("/getHomepagePlateById")
    public CommonResult getHomepagePlateById(@RequestParam("homepagePlateId") long id) {
        HomepagePlateEntity homepagePlateEntity = homepagePlateService.getHomepagePlateById(id);
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), homepagePlateEntity);
    }

    /**
     * 删除板块
     */
    @DeleteMapping("/delete")
    public CommonResult delete(@RequestBody Long[] companyIds){
        homepagePlateService.deleteBatch(companyIds);
        return CommonResult.success();
    }

    /**
     * 启用 / 禁用 导航
     */
    @PutMapping("/enable")
    public CommonResult enable(@RequestParam("plateId") Long plateId,
                               @RequestParam("enable") Boolean enable){
        int num = homepagePlateService.enable(plateId, enable);
        if (num < 0){
            return CommonResult.fail(ResultCodeEnum.ENABLE_FAIL.getCode(),ResultCodeEnum.ENABLE_FAIL.getMessage());
        }
        return CommonResult.success();
    }
}
