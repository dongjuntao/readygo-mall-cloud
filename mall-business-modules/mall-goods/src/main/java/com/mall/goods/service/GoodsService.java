package com.mall.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.common.base.utils.PageUtil;
import com.mall.goods.entity.GoodsEntity;
import com.mall.goods.vo.GoodsCountByCategoryVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2021/6/30 16:53
 * @Version 1.0
 */
public interface GoodsService extends IService<GoodsEntity> {

    PageUtil queryPage(Integer pageNum,
                       Integer pageSize,
                       String name,
                       Long adminUserId,
                       String categoryIds,
                       String goodsStatus);

    GoodsEntity getGoodsAndSku(Long id);

    int applyOnSale(Long goodsId);

    int offShelf(Long goodsId);

    int audit(Long goodsId,boolean isAudit);

    List<GoodsEntity> getAllGoodsList(Long adminUserId);

    /**
     * 根据id集合，查询所有商户信息
     * @param ids
     * @return
     */
    List<GoodsEntity> getByIds(Long[] ids);

    List<GoodsEntity> getAllGoodsWithDetail();

    int count(String goodsStatus, Long adminUserId); //商品总数

    List<GoodsCountByCategoryVO> getGoodsCountByCategory(Long adminUserId);
}
