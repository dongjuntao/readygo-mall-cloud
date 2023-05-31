package com.mall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.common.base.utils.PageUtil;
import com.mall.member.entity.MemberEntity;

import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 会员service
 * @Date 2022/4/14 20:35
 * @Version 1.0
 */
public interface MemberService extends IService<MemberEntity> {

    /**
     * 会员注册账号
     * @param member
     */
    int register(MemberEntity member);

    /**
     * 用户登录
     * @param member
     */
    void login(MemberEntity member);

    /**
     * 根据用户名查看用户
     * @return
     */
    MemberEntity getMemberByParams(String userName, Long id);
    /**
     * 修改会员信息
     * @param memberEntity
     * @return
     */
    int updateMemberEntity(MemberEntity memberEntity);

    /**
     * 修改密码
     * @param memberId
     * @param oldPassword
     * @param newPassword
     * @return
     */
    int updatePassword(Long memberId, String oldPassword, String newPassword);

    /**
     * 分页查询所有会员
     * @return
     */
    PageUtil queryPage(Integer pageNum, Integer pageSize, String userName);

    /**
     * 删除用户
     */
    void deleteBatch(Long[] userIds);

    /**
     * 根据主键id获取会员实体
     * @param id
     * @return
     */
    MemberEntity getMemberById(long id);
}
