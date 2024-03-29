package com.mall.coupon.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.ibatis.type.JdbcType;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author DongJunTao
 * @Description 优惠券实体类
 * @Date 2021/10/29 20:35
 * @Version 1.0
 */
@Data
@TableName("coupon")
public class CouponEntity {

    @TableId
    private Long id;
    /**
     * 优惠券名称
     */
    private String name;
    /**
     * 优惠券来源（优惠券来源（0：平台；1：商家））
     */
    private Integer source;
    /**
     * 商户id
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED,jdbcType = JdbcType.BIGINT)
    private Long adminUserId;
    /**
     * 优惠券类型（0：满减券；1：满折券）
     */
    private Integer type;
    /**
     * 使用门槛（0：无门槛 1：有门槛）
     */
    private Integer useThreshold;
    /**
     * 有门槛时最低消费
     */
    private Double minConsumption;
    /**
     * 优惠额度（如果是满减券，该字段是减钱数，如果是满折券，该字段是打折数）
     */
    private Double discountAmount;
    /**
     * 使用范围（0：全部商品；1：指定分类， 2：指定商品）
     */
    private Integer useScope;
    /**
     * 支持的商品分类ids
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED,jdbcType = JdbcType.VARCHAR)
    private String goodsCategoryIds;
    /**
     * 支持的商品ids
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED,jdbcType = JdbcType.VARCHAR)
    private String goodsIds;
    /**
     * 适用会员（普通会员，青铜会员，白银会员，黄金会员，铂金会员，钻石会员，最强买家）
     */
    private String applicableMember;
    /**
     * 发行数量
     */
    private Integer issueNumber;
    /**
     * 领取数量
     */
    private Integer receivedNumber;
    /**
     * 有效期开始
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date validPeriodStart;
    /**
     * 有效期结束
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date validPeriodEnd;
    /**
     * 每人限领多少张
     */
    private Integer  perLimit;
    /**
     * 状态（0：禁用；1：启用）
     */
    private Boolean status;
    /**
     * 审核状态（0：待审核，1：审核通过，2：审核拒绝）
     */
    private Integer authStatus;
    /**
     * 审核意见
     */
    private String authOpinion;
    /**
     * 创建人id
     */
    private Long createBy;
    /**
     * 修改人id
     */
    private Long updateBy;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 所属商户名称
     */
    @TableField(exist = false)
    private String merchantName;

}
