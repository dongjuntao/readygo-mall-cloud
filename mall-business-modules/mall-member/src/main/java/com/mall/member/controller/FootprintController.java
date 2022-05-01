package com.mall.member.controller;

import com.mall.common.base.CommonResult;
import com.mall.common.base.enums.ResultCodeEnum;
import com.mall.common.base.utils.PageUtil;
import com.mall.goods.api.front.GoodsService;
import com.mall.member.entity.FootprintEntity;
import com.mall.member.service.FootprintService;
import com.mall.member.vo.FootprintGoodsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2022/4/29 20:46
 * @Version 1.0
 */
@RestController
@RequestMapping("footprint")
public class FootprintController {

    @Autowired
    private FootprintService footprintService;

    @Autowired
    private GoodsService goodsService;

    /**
     * 分页查询会员足迹列表
     */
    @GetMapping("/list")
    public CommonResult list(@RequestParam Map<String, Object> params){
        PageUtil page = footprintService.getByPage(params);
        //根据分页结果查询商品信息，并设置属性
        List list = page.getList();
        Long[] goodsIds = new Long[list.size()];
        for (int i=0; i<list.size(); i++) {
            FootprintEntity footprint = (FootprintEntity) list.get(i);
            goodsIds[i] = footprint.getGoodsId();
        }
        //远程调用商品服务，获取商品信息
        CommonResult result = goodsService.listByIds(goodsIds);
        if (result != null && "200".equals(result.getCode())) {
            List resultList = (List) result.getData();
            for (int i=0;i<list.size();i++) {
                for(int j=0; j<resultList.size(); j++) {
                    Map<String,Object> map = (Map)resultList.get(j);
                    if (((FootprintEntity)list.get(i)).getGoodsId().toString().equals(map.get("id").toString())) {
                        FootprintGoodsVO footprintGoodsVO = new FootprintGoodsVO();
                        Map firstSku = (Map)((List)map.get("goodsSkuList")).get(0);
                        footprintGoodsVO.setId(Long.valueOf(map.get("id").toString()));
                        footprintGoodsVO.setName(map.get("name").toString());
                        footprintGoodsVO.setImage(map.get("images").toString().split(",")[0]);
                        footprintGoodsVO.setPrice(new BigDecimal(firstSku.get("sellingPrice").toString()));
                        footprintGoodsVO.setSkuId(Long.valueOf(firstSku.get("id").toString()));
                        ((FootprintEntity) list.get(i)).setFootprintGoodsVO(footprintGoodsVO);
                        break;//一旦找到，退出当前循环，减少循环次数
                    }
                }
            }
        }
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), page);
    }
}
