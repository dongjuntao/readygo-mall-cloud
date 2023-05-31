package com.mall.member.controller;

import com.mall.admin.api.feign.front.FeignAdminUserService;
import com.mall.common.base.CommonResult;
import com.mall.common.base.enums.ResultCodeEnum;
import com.mall.common.base.utils.CurrentUserContextUtil;
import com.mall.member.entity.CollectGoodsEntity;
import com.mall.member.entity.CollectShopEntity;
import com.mall.member.service.CollectGoodsService;
import com.mall.member.service.CollectShopService;
import com.mall.member.vo.CollectGoodsVO;
import com.mall.member.vo.CollectShopVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 店铺收藏controller
 * @Date 2022/5/2 10:59
 * @Version 1.0
 */
@RestController
@RequestMapping("collect/shop")
public class CollectShopController {

    @Autowired
    private CollectShopService collectShopService;

    @Autowired
    private FeignAdminUserService feignAdminUserService;

    /**
     * 新增店铺收藏
     * @param collectShop
     * @return
     */
    @PostMapping("save")
    public CommonResult save(@RequestBody CollectShopEntity collectShop) {
        collectShop.setMemberId(CurrentUserContextUtil.getCurrentUserInfo().getUserId());
        int num = collectShopService.saveCollectShop(collectShop);
        return num > 0 ? CommonResult.success() : CommonResult.fail();
    }

    /**
     * 取消店铺收藏
     * @param merchantId
     * @return
     */
    @DeleteMapping("delete")
    public CommonResult delete(@RequestParam Long merchantId) {
        Long userId = CurrentUserContextUtil.getCurrentUserInfo().getUserId();
        int num = collectShopService.deleteCollectShop(userId, merchantId);
        return num > 0 ? CommonResult.success() : CommonResult.fail();
    }

    /**
     * 判断是否已收藏
     * @param merchantId
     * @return
     */
    @GetMapping("/isCollected")
    public CommonResult isCollected(@RequestParam long merchantId) {
        Long userId = CurrentUserContextUtil.getCurrentUserInfo().getUserId();
        boolean isCollected = collectShopService.isCollected(userId, merchantId);
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), isCollected);
    }

    /**
     * 查询商品收藏列表
     * @param params
     * @return
     */
    @GetMapping("listAll")
    public CommonResult getAllCollectShop() {
        List<CollectShopEntity> collectShopList = collectShopService
                .listAll(CurrentUserContextUtil.getCurrentUserInfo().getUserId());
        if (collectShopList.size() == 0) {
            return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), collectShopList);
        }
        Long[] merchantIds = new Long[collectShopList.size()];
        for (int i=0; i<collectShopList.size(); i++) {
            merchantIds[i] = collectShopList.get(i).getMerchantId();
        }
        //远程调用店铺服务，获取店铺信息
        CommonResult result = feignAdminUserService.listByIds(merchantIds);
        if (result == null || !"200".equals(result.getCode())) {
            return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), collectShopList);
        }
        List resultList = (List) result.getData();
        for (int i=0;i<collectShopList.size();i++) {
            for(int j=0; j<resultList.size(); j++) {
                Map<String,Object> map = (Map)resultList.get(j);
                if ((collectShopList.get(i)).getMerchantId().toString().equals(map.get("id").toString())) {
                    CollectShopVO collectShopVO = new CollectShopVO();
                    collectShopVO.setId(Long.valueOf(map.get("id").toString()));
                    collectShopVO.setName(map.get("name").toString());
                    collectShopVO.setAvatar(map.get("avatar").toString());
                    (collectShopList.get(i)).setCollectShopVO(collectShopVO);
                    break;//一旦找到，退出当前循环，减少循环次数
                }
            }
        }
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), collectShopList);
    }

}
