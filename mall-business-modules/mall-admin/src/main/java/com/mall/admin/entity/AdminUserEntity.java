package com.mall.admin.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author DongJunTao
 * @Description 后台用户表
 * @Date 2021/5/28 9:38
 * @Version 1.0
 */
@Data
@TableName("admin_user")
public class AdminUserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户主键id
     */
    @TableId
    private Long id;
    /**
     * 后台用户名称【管理员或商户】
     */
    private String name;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String password;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 固定电话
     */
    private String fixedTelephone;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 状态  0：禁用   1：正常
     */
    private Integer status;
    /**
     * 审核状态（商户申请后，需要审核 0:待审核，1:已通过 2:已拒绝）
     * 平台自营账号自动审核通过【无需审核】
     */
    private Integer authStatus;
    /**
     * 审核意见
     */
    private String authOpinion;
    /**
     * 资质材料，文件地址，逗号分隔
     */
    private String qualificationMaterials;
    /**
     * 管理员类型用户类型
     * 0:【平台管理员】（该类管理员属于平台所有，管理平台相关事宜）
     * 1:【店铺管理员】包括【入驻商户】【自营商户】
     */
    private Integer userType;
    /**
     * 商户（店铺）类型
     * 0: 入驻商户
     * 1: 自营商户
     */
    private Integer merchantType;
    /**
     * 所属区域
     */
    private String regions;
    /**
     * 详细地址
     */
    private String address;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 角色ID列表
     */
    @TableField(exist=false)
    private List<Long> roleIdList;

}
