package com.mall.admin.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author DongJunTao
 * @Description 板块-商品关联表
 * @Date 2022/3/19 16:46
 * @Version 1.0
 */
@Data
@TableName("homepage_plate_goods_related")
public class HomepagePlateGoodsRelatedEntity {

    @TableId
    private Long id;

    /**
     * 板块id
     */
    private Long plateId;

    /**
     * 关联的商品id
     */
    private Long goodsId;
}
