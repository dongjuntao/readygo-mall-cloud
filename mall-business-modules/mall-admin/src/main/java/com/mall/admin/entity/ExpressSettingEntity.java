package com.mall.admin.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2021/9/8 21:32
 * @Version 1.0
 */
@Data
@TableName("express_setting")
public class ExpressSettingEntity {

    @TableId
    private Long id;
    /**
     * 商家id
     */
    private Long adminUserId;
    /**
     * 快递公司id
     */
    private Long logisticsCompanyId;
    /**
     * 是否开启
     */
    private Boolean enable;
    /**
     * 是否是默认
     */
    private Boolean isDefault;
}
