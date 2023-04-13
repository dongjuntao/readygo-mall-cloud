package com.mall.member.controller;

import com.mall.common.base.CommonResult;
import com.mall.common.base.enums.ResultCodeEnum;
import com.mall.common.base.utils.CurrentUserContextUtil;
import com.mall.goods.api.FeignFrontGoodsService;
import com.mall.member.entity.CollectGoodsEntity;
import com.mall.member.service.CollectGoodsService;
import com.mall.member.vo.CollectGoodsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 商品收藏controller
 * @Date 2022/5/2 10:59
 * @Version 1.0
 */
@RestController
@RequestMapping("collect/goods")
public class CollectGoodsController {

    @Autowired
    private CollectGoodsService collectGoodsService;

    @Autowired
    private FeignFrontGoodsService feignFrontGoodsService;

    /**
     * 新增商品收藏
     * @param collectGoods
     * @return
     */
    @PostMapping("save")
    public CommonResult save(@RequestBody CollectGoodsEntity collectGoods) {
        collectGoods.setMemberId(CurrentUserContextUtil.getCurrentUserInfo().getUserId());
        int num = collectGoodsService.saveCollectGoods(collectGoods);
        if (num == -1) {
            return CommonResult.success(ResultCodeEnum.COLLECT_GOODS_IS_EXIST.getCode(),ResultCodeEnum.COLLECT_GOODS_IS_EXIST.getMessage());
        }
        return num > 0 ? CommonResult.success() : CommonResult.fail();
    }

    /**
     * 取消商品收藏
     * @param goodsId
     * @return
     */
    @DeleteMapping("delete")
    public CommonResult delete(@RequestParam Long goodsId) {
        Long userId = CurrentUserContextUtil.getCurrentUserInfo().getUserId();
        int num = collectGoodsService.deleteCollectGoods(userId, goodsId);
        return num > 0 ? CommonResult.success() : CommonResult.fail();
    }

    /**
     * 判断是否已收藏
     * @param goodsId
     * @return
     */
    @GetMapping("/isCollected")
    public CommonResult isCollected(@RequestParam long goodsId) {
        Long userId = CurrentUserContextUtil.getCurrentUserInfo().getUserId();
        boolean isCollected = collectGoodsService.isCollected(userId, goodsId);
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), isCollected);
    }

    /**
     * 查询商品收藏列表
     * @param params
     * @return
     */
    @GetMapping("listAll")
    public CommonResult getAllCollectGoods(@RequestParam Map<String, Object> params) {
        params.put("userId", CurrentUserContextUtil.getCurrentUserInfo().getUserId());
        List<CollectGoodsEntity> collectGoodsList = collectGoodsService.listAll(params);
        if (collectGoodsList.size() == 0) {
            return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), collectGoodsList);
        }
        Long[] goodsIds = new Long[collectGoodsList.size()];
        for (int i=0; i<collectGoodsList.size(); i++) {
            goodsIds[i] = collectGoodsList.get(i).getGoodsId();
        }
        //远程调用商品服务，获取商品信息
        CommonResult result = feignFrontGoodsService.listByIds(goodsIds);
        if (result == null || !"200".equals(result.getCode())) {
            return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), collectGoodsList);
        }
        List resultList = (List) result.getData();
        for (int i=0;i<collectGoodsList.size();i++) {
            for(int j=0; j<resultList.size(); j++) {
                Map<String,Object> map = (Map)resultList.get(j);
                if ((collectGoodsList.get(i)).getGoodsId().toString().equals(map.get("id").toString())) {
                    CollectGoodsVO collectGoodsVO = new CollectGoodsVO();
                    Map firstSku = (Map)((List)map.get("goodsSkuList")).get(0);
                    collectGoodsVO.setId(Long.valueOf(map.get("id").toString()));
                    collectGoodsVO.setName(map.get("name").toString());
                    collectGoodsVO.setImage(map.get("images").toString().split(",")[0]);
                    collectGoodsVO.setPrice(new BigDecimal(firstSku.get("sellingPrice").toString()));
                    collectGoodsVO.setSkuId(Long.valueOf(firstSku.get("id").toString()));
                    (collectGoodsList.get(i)).setCollectGoodsVO(collectGoodsVO);
                    break;//一旦找到，退出当前循环，减少循环次数
                }
            }
        }
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), collectGoodsList);
    }

}
