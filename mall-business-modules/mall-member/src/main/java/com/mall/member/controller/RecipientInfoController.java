package com.mall.member.controller;

import com.mall.admin.api.feign.FeignRegionService;
import com.mall.common.base.CommonResult;
import com.mall.common.base.enums.ResultCodeEnum;
import com.mall.member.entity.RecipientInfoEntity;
import com.mall.member.service.RecipientInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 收货信息controller
 * @Date 2022/4/16 18:15
 * @Version 1.0
 */
@RestController
@RequestMapping("recipientInfo")
public class RecipientInfoController {

    @Autowired
    private RecipientInfoService recipientInfoService;

    @Autowired
    private FeignRegionService feignRegionService;

    /**
     * 查询收货信息列表
     * @param params
     * @return
     */
    @GetMapping("list")
    public CommonResult getRegionList(@RequestParam Map<String, Object> params) {
        List<RecipientInfoEntity> shippingInfoList = recipientInfoService.listAll(params);
        //所属地区处理
        if(!CollectionUtils.isEmpty(shippingInfoList)) {
            for (int i=0; i<shippingInfoList.size(); i++){
                CommonResult result = feignRegionService.getRegionsNameByRegions(shippingInfoList.get(i).getRegions());
                if (result != null || "200".equals(result.getCode())) {
                    shippingInfoList.get(i).setRegionNames(String.valueOf(result.getData()));
                }
            }
        }
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), shippingInfoList);
    }

    /**
     * 新增收货信息
     * @param recipientInfoEntity
     * @return
     */
    @PostMapping("save")
    public CommonResult save(@RequestBody RecipientInfoEntity recipientInfoEntity) {
        recipientInfoEntity.setCreateTime(new Date());
        int num = recipientInfoService.saveRecipientInfo(recipientInfoEntity);
        return num > 0 ? CommonResult.success() : CommonResult.fail();
    }

    /**
     * 修改收货信息
     * @param recipientInfoEntity
     * @return
     */
    @PutMapping("update")
    public CommonResult update(@RequestBody RecipientInfoEntity recipientInfoEntity) {
        int num = recipientInfoService.updateRecipientInfo(recipientInfoEntity);
        return num > 0 ? CommonResult.success() : CommonResult.fail();
    }

    /**
     * 删除发货信息
     * @param id
     * @return
     */
    @DeleteMapping("delete")
    public CommonResult delete(@RequestParam Long id) {
        int num = recipientInfoService.deleteRecipientInfo(id);
        return num > 0 ? CommonResult.success() : CommonResult.fail();
    }

    /**
     * 根据主键id获取用户实体
     * @param id
     * @return
     */
    @GetMapping("/getRecipientInfoById")
    public CommonResult getRecipientInfoById(@RequestParam long id) {
        RecipientInfoEntity shippingInfo = recipientInfoService.getRecipientInfoById(id);
        CommonResult result = feignRegionService.getRegionsNameByRegions(shippingInfo.getRegions());
        if (result != null || "200".equals(result.getCode())) {
            shippingInfo.setRegionNames(String.valueOf(result.getData()));
        }
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), shippingInfo);
    }

    /**
     * 设为默认 / 取消默认
     * @param id
     * @return
     */
    @PutMapping("updateIsDefault")
    public CommonResult updateIsDefault(@RequestParam("id") Long id, @RequestParam("isDefault") Boolean isDefault) {
        recipientInfoService.updateIsDefault(id, isDefault);
        return CommonResult.success();
    }
}
