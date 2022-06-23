package com.mall.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author DongJunTao
 * @Description 订单详细表
 * @Date 2022/6/15 22:16
 * @Version 1.0
 */
@Data
@TableName("order_detail")
public class OrderDetailEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long orderId;

    /**
     * 子订单号
     */
    private String subCode;

    /**
     * 商品id
     */
    private Long goodsId;

    /**
     * 商品skuId
     */
    private Long goodsSkuId;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 商品图片
     */
    private String goodsImage;
    /**
     * 商品销售价格
     */
    private BigDecimal goodsSellingPrice;
    /**
     * 购买数量
     */
    private Integer goodsCount;
    /**
     * 小计
     */
    private BigDecimal goodsSubTotal;

    /**
     * 收件人姓名
     */
    private String recipientName;

    /**
     * 收件人详细地址
     */
    private String recipientDetailAddress;

    /**
     * 收件人手机号码
     */
    private String recipientMobile;

    /**
     * 收货地区名称（省、市、区县）如（安徽省 淮南市 寿县）
     */
    private String regionNames;
}
