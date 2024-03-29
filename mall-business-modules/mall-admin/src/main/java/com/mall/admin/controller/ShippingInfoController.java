package com.mall.admin.controller;

import com.mall.admin.entity.ShippingInfoEntity;
import com.mall.admin.service.RegionService;
import com.mall.admin.service.ShippingInfoService;
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
 * @Description
 * @Date 2021/8/31 22:30
 * @Version 1.0
 */
@RestController
@RequestMapping("store/shippingInfo")
public class ShippingInfoController {

    @Autowired
    private ShippingInfoService shippingInfoService;

    @Autowired
    private RegionService regionService;

    /**
     * 查询发货信息列表
     * @param pageNum
     * @param pageSize
     * @param name
     * @param adminUserId
     * @param mobile
     * @return
     */
    @GetMapping("list")
    public CommonResult getRegionList(@RequestParam(value = "pageNum", required = false) Integer pageNum,
                                      @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                      @RequestParam(value = "name", required = false) String name,
                                      @RequestParam(value = "adminUserId", required = false) Long adminUserId,
                                      @RequestParam(value = "mobile", required = false) String mobile) {
        PageUtil page = shippingInfoService.queryPage(pageNum,pageSize,name,adminUserId,mobile);
        //所属地区处理
        if(page.getList().size() > 0) {
            List<ShippingInfoEntity> shippingInfoEntityList = ((List<ShippingInfoEntity>)page.getList());
            for (int i=0; i<shippingInfoEntityList.size(); i++){
                shippingInfoEntityList.get(i).setRegionNames(regionService.getRegionsNameByRegions(shippingInfoEntityList.get(i).getRegions()));
            }
        }
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), page);
    }

    /**
     * 新增发货信息
     * @param shippingInfoEntity
     * @return
     */
    @PostMapping("save")
    public CommonResult save(@RequestBody ShippingInfoEntity shippingInfoEntity) {
        shippingInfoEntity.setCreateTime(new Date());
        int num = shippingInfoService.saveShippingInfo(shippingInfoEntity);
        return num > 0 ? CommonResult.success() : CommonResult.fail();
    }

    /**
     * 修改发货信息
     * @param shippingInfoEntity
     * @return
     */
    @PutMapping("update")
    public CommonResult update(@RequestBody ShippingInfoEntity shippingInfoEntity) {
        int num = shippingInfoService.updateShippingInfo(shippingInfoEntity);
        return num > 0 ? CommonResult.success() : CommonResult.fail();
    }

    /**
     * 删除发货信息
     * @param id
     * @return
     */
    @DeleteMapping("delete")
    public CommonResult delete(@RequestParam Long id) {
        int num = shippingInfoService.deleteShippingInfo(id);
        return num > 0 ? CommonResult.success() : CommonResult.fail();
    }

    /**
     * 根据主键id获取用户实体
     * @param id
     * @return
     */
    @GetMapping("/getShippingInfoById")
    public CommonResult getShippingInfoById(@RequestParam long id) {
        ShippingInfoEntity shippingInfo = shippingInfoService.getShippingInfoById(id);
        shippingInfo.setRegionNames(regionService.getRegionsNameByRegions(shippingInfo.getRegions()));
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), shippingInfo);
    }

    /**
     * 设为默认 / 取消默认
     * @param id
     * @return
     */
    @PutMapping("updateIsDefault/{id}")
    public CommonResult updateIsDefault(@PathVariable("id") Long id, @RequestParam("isDefault") Boolean isDefault) {
        shippingInfoService.updateIsDefault(id, isDefault);
        return CommonResult.success();
    }
}
