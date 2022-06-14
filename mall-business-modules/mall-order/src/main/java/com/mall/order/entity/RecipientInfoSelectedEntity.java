package com.mall.order.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author DongJunTao
 * @Description 购物车内已选择的收货信息
 * @Date 2022/5/28 17:24
 * @Version 1.0
 */
@Data
@TableName("recipient_info_selected")
public class RecipientInfoSelectedEntity {

    @TableField
    private Long id;
    /**
     * 会员id
     */
    private Long memberId;
    /**
     * 收货信息id
     */
    private Long recipientInfoId;
}
