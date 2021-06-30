package com.mall.goods.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author DongJunTao
 * @Description 规格详细信息表
 * @Date 2021/6/28 16:11
 * @Version 1.0
 */
@Data
@TableName("goods_specifications_detail")
public class GoodsSpecificationsDetailEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 规格详细信息id
     */
    @TableId
    private Long id;
    /**
     * 规格id
     */
    private Long goodsSpecificationsId;
    /**
     * 规格值
     */
    private String value;
    /**
     * 规格绑定图片
     */
    private String bindImage;
    /**
     * 是否启用
     */
    private Boolean enable;
}
