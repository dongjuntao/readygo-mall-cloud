package com.mall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
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
     * @param params
     * @return
     */
    MemberEntity getMemberByParams(Map<String, Object> params);


}
