package com.mall.member.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mall.member.vo.CollectGoodsVO;
import lombok.Data;

/**
 * @Author DongJunTao
 * @Description 商品收藏表
 * @Date 2022/5/2 10:45
 * @Version 1.0
 */
@Data
@TableName("collect_goods")
public class CollectGoodsEntity {

    @TableField
    private Long id;
    /**
     * 会员id
     */
    private Long memberId;
    /**
     * 商品id
     */
    private Long goodsId;
    /**
     * 关联的收藏商品
     */
    @TableField(exist = false)
    private CollectGoodsVO collectGoodsVO;
}
