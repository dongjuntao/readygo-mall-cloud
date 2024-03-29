package com.mall.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.common.base.utils.MapUtil;
import com.mall.common.base.utils.PageBuilder;
import com.mall.common.base.utils.PageUtil;
import com.mall.member.entity.MemberEntity;
import com.mall.member.mapper.MemberMapper;
import com.mall.member.service.MemberService;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 会员service实现类
 * @Date 2022/4/14 20:38
 * @Version 1.0
 */
@Service("memberService")
public class MemberServiceImpl extends ServiceImpl<MemberMapper, MemberEntity> implements MemberService {

    /**
     * 会员注册账号
     * @param member
     */
    @Override
    public int register(MemberEntity member) {
        //用户名不能重复
        QueryWrapper<MemberEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", member.getUserName());
        if (this.getOne(queryWrapper) != null) {
            return -1; //说明有用户名重复
        };
        member.setCreateTime(new Date());
        member.setPassword(new BCryptPasswordEncoder().encode(member.getPassword()));//密码加密
        return this.baseMapper.insert(member);
    }

    /**
     * 用户登录
     * @param member
     */
    @Override
    public void login(MemberEntity member) {

    }

    @Override
    public MemberEntity getMemberByParams(String userName, Long id) {
        return baseMapper.selectOne(
                new QueryWrapper<MemberEntity>()
                        .eq(StringUtils.isNotBlank(userName), "user_name", userName)
                        .ne(id != null, "id",id));//排除id
    }

    @Override
    public int updateMemberEntity(MemberEntity memberEntity) {
        memberEntity.setUpdateTime(new Date());
        //密码不为空，修改，为空，保持之前的密码不变
        if(!StringUtils.isEmpty(memberEntity.getPassword())){
            memberEntity.setPassword(new BCryptPasswordEncoder().encode(memberEntity.getPassword()));
        }else {
            memberEntity.setPassword(this.getMemberById(memberEntity.getId()).getPassword());
        }
        return baseMapper.updateById(memberEntity);
    }

    /**
     * 修改密码
     * @param memberId 会员id
     * @param oldPassword 原密码
     * @param newPassword 新密码
     * @return
     */
    @Override
    public int updatePassword(Long memberId, String oldPassword, String newPassword) {
        MemberEntity member = baseMapper.selectById(memberId);
        if (member == null) {
            return -2;
        }
        //校验原密码
        String encodeOldPassword = member.getPassword();
        BCryptPasswordEncoder bpe = new BCryptPasswordEncoder();
        if (!bpe.matches(oldPassword, encodeOldPassword)) {
            return -1;
        }
        member.setPassword(bpe.encode(newPassword));
        return baseMapper.updateById(member);
    }

    /**
     * 分页查询所有用户
     * @param userName 用户名
     * @return
     */
    @Override
    public PageUtil queryPage(Integer pageNum, Integer pageSize, String userName) {
        Map<String,Object> pageParams = new MapUtil().put("pageNum",pageNum).put("pageSize",pageSize);
        IPage<MemberEntity> page = this.page(
                new PageBuilder<MemberEntity>().getPage(pageParams),
                new QueryWrapper<MemberEntity>()
                        .like(StringUtils.isNotBlank(userName), "user_name", userName)
        );
        return new PageUtil(page);
    }

    /**
     * 删除用户
     * @param userIds
     */
    @Override
    public void deleteBatch(Long[] userIds) {
        this.removeByIds(Arrays.asList(userIds));
    }

    /**
     * 根据主键id获取会员实体
     * @param id
     * @return
     */
    @Override
    public MemberEntity getMemberById(long id) {
        return this.getById(id);
    }

    @Override
    public int count() {
        QueryWrapper<MemberEntity> queryWrapper = new QueryWrapper<>();
        return baseMapper.selectCount(queryWrapper);
    }
}
