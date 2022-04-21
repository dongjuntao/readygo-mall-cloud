package com.mall.admin.controller;

import com.mall.admin.entity.HomepageCarouselEntity;
import com.mall.admin.service.HomepageCarouselService;
import com.mall.common.base.CommonResult;
import com.mall.common.base.enums.ResultCodeEnum;
import com.mall.common.base.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 商城首页轮播图
 * @Date 2022/3/15 20:09
 * @Version 1.0
 */
@RestController
@RequestMapping("homepage/carousel")
public class HomepageCarouselController {

    @Autowired
    private HomepageCarouselService homepageCarouselService;

    /**
     * 分页查询商城首页轮播图列表
     */
    @GetMapping("/list")
    public CommonResult list(@RequestParam Map<String, Object> params){
        PageUtil page = homepageCarouselService.getByPage(params);
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), page);
    }

    /**
     * 所有轮播图列表（不分页）
     */
    @GetMapping("/listAll")
    public CommonResult listAll(@RequestParam Map<String, Object> params){
        List<HomepageCarouselEntity> homepageCarouselEntityList = homepageCarouselService.getByParams(params);
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), homepageCarouselEntityList);
    }

    /**
     * 保存轮播图
     */
    @PostMapping("/save")
    public CommonResult save(@RequestBody HomepageCarouselEntity homepageCarouselEntity){
        int num = homepageCarouselService.saveHomepageCarousel(homepageCarouselEntity);
        if (num == -1){
            return CommonResult.fail(ResultCodeEnum.HOMEPAGE_CAROUSEL_EXIST.getCode(),ResultCodeEnum.HOMEPAGE_CAROUSEL_EXIST.getMessage());
        }
        return CommonResult.success();
    }

    /**
     * 修改轮播图
     */
    @PutMapping("/update")
    public CommonResult update(@RequestBody HomepageCarouselEntity homepageCarouselEntity){
        int num = homepageCarouselService.updateHomepageCarousel(homepageCarouselEntity);
        if (num == -1){
            return CommonResult.fail(ResultCodeEnum.HOMEPAGE_CAROUSEL_EXIST.getCode(),ResultCodeEnum.HOMEPAGE_CAROUSEL_EXIST.getMessage());
        }
        return CommonResult.success();
    }

    /**
     * 根据主键id获取轮播图
     * @param id
     * @return
     */
    @GetMapping("/getHomepageCarouselById")
    public CommonResult getHomepageNavbarById(@RequestParam("homepageCarouselId") long id) {
        HomepageCarouselEntity homepageCarouselEntity = homepageCarouselService.getHomepageCarouselById(id);
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), homepageCarouselEntity);
    }

    /**
     * 删除轮播图
     */
    @DeleteMapping("/delete")
    public CommonResult delete(@RequestBody Long[] companyIds){
        homepageCarouselService.deleteBatch(companyIds);
        return CommonResult.success();
    }

    /**
     * 启用 / 禁用 轮播图
     */
    @PutMapping("/enable")
    public CommonResult enable(@RequestParam("carouselId") Long carouselId,
                               @RequestParam("enable") Boolean enable){
        int num = homepageCarouselService.enable(carouselId, enable);
        if (num < 0){
            return CommonResult.fail(ResultCodeEnum.ENABLE_FAIL.getCode(),ResultCodeEnum.ENABLE_FAIL.getMessage());
        }
        return CommonResult.success();
    }
}
