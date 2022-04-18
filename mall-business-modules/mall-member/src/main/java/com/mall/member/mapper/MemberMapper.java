package com.mall.member.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.member.entity.MemberEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author DongJunTao
 * @Description 会员mapper
 * @Date 2022/4/14 17:23
 * @Version 1.0
 */
@Mapper
public interface MemberMapper extends BaseMapper<MemberEntity> {
}
