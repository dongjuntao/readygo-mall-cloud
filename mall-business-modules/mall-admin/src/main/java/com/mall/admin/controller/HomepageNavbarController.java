package com.mall.admin.controller;

import com.mall.admin.entity.HomepageNavbarEntity;
import com.mall.admin.service.HomepageNavbarService;
import com.mall.common.base.CommonResult;
import com.mall.common.base.enums.ResultCodeEnum;
import com.mall.common.base.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 商城首页导航
 * @Date 2022/2/10 22:54
 * @Version 1.0
 */
@RestController
@RequestMapping("homepage/navbar")
public class HomepageNavbarController {

    @Autowired
    private HomepageNavbarService homepageNavbarService;

    /**
     * 分页查询商城首页导航列表
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @param name 导航名称
     * @return
     */
    @GetMapping("/list")
    public CommonResult list(@RequestParam(value = "pageNum",required = false) Integer pageNum,
                             @RequestParam(value = "pageSize",required = false) Integer pageSize,
                             @RequestParam(value = "name",required = false) String name){
        PageUtil page = homepageNavbarService.getByPage(pageNum, pageSize, name);
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), page);
    }

    /**
     * 根据条件查询所有导航 (不分页)
     * @param name 导航名称
     * @return
     */
    @GetMapping("/listAll")
    public CommonResult listAll(@RequestParam(value = "name",required = false) String name){
        List<HomepageNavbarEntity> homepageNavbarEntityList = homepageNavbarService.getByParams(name);
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), homepageNavbarEntityList);
    }

    /**
     * 保存导航
     */
    @PostMapping("/save")
    public CommonResult save(@RequestBody HomepageNavbarEntity homepageNavbarEntity){
        int num = homepageNavbarService.saveHomepageNavbar(homepageNavbarEntity);
        if (num == -1){
            return CommonResult.fail(ResultCodeEnum.HOMEPAGE_NAVBAR_EXIST.getCode(),ResultCodeEnum.HOMEPAGE_NAVBAR_EXIST.getMessage());
        }
        return CommonResult.success();
    }

    /**
     * 修改导航
     */
    @PutMapping("/update")
    public CommonResult update(@RequestBody HomepageNavbarEntity homepageNavbarEntity){
        int num = homepageNavbarService.updateHomepageNavbar(homepageNavbarEntity);
        if (num == -1){
            return CommonResult.fail(ResultCodeEnum.HOMEPAGE_NAVBAR_EXIST.getCode(),ResultCodeEnum.HOMEPAGE_NAVBAR_EXIST.getMessage());
        }
        return CommonResult.success();
    }

    /**
     * 根据主键id获取导航
     * @param id
     * @return
     */
    @GetMapping("/getHomepageNavbarById")
    public CommonResult getHomepageNavbarById(@RequestParam("homepageNavbarId") long id) {
        HomepageNavbarEntity homepageNavbarEntity = homepageNavbarService.getHomepageNavbarById(id);
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), homepageNavbarEntity);
    }

    /**
     * 删除导航
     */
    @DeleteMapping("/delete")
    public CommonResult delete(@RequestBody Long[] companyIds){
        homepageNavbarService.deleteBatch(companyIds);
        return CommonResult.success();
    }

    /**
     * 启用 / 禁用 导航
     */
    @PutMapping("/enable")
    public CommonResult enable(@RequestParam("navbarId") Long navbarId,
                               @RequestParam("enable") Boolean enable){
        int num = homepageNavbarService.enable(navbarId, enable);
        if (num < 0){
            return CommonResult.fail(ResultCodeEnum.ENABLE_FAIL.getCode(),ResultCodeEnum.ENABLE_FAIL.getMessage());
        }
        return CommonResult.success();
    }
}
