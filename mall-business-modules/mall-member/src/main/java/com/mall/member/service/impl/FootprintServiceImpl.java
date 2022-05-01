package com.mall.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.common.base.utils.CurrentUserContextUtil;
import com.mall.common.base.utils.PageBuilder;
import com.mall.common.base.utils.PageUtil;
import com.mall.member.entity.FootprintEntity;
import com.mall.member.entity.MemberEntity;
import com.mall.member.mapper.FootprintMapper;
import com.mall.member.service.FootprintService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 会员足迹service实现类
 * @Date 2022/4/28 20:53
 * @Version 1.0
 */
@Service("footprintService")
public class FootprintServiceImpl extends ServiceImpl<FootprintMapper, FootprintEntity> implements FootprintService {

    /**
     * 获取用户足迹信息【包括商品信息】
     * @param params
     * @return
     */
    @Override
    public PageUtil getByPage(Map<String, Object> params) {
        Page<FootprintEntity> page = (Page<FootprintEntity>)new PageBuilder<FootprintEntity>().getPage(params);
        QueryWrapper<FootprintEntity> wrapper = new QueryWrapper<>();
        Long memberId = CurrentUserContextUtil.getCurrentUserInfo().getUserId();
        wrapper.eq(memberId != null,"member_id", memberId);
        wrapper.orderByDesc("update_time","create_time");
        IPage<FootprintEntity> iPage = baseMapper.selectPage(page, wrapper);
        return new PageUtil(iPage);
    }

    /**
     * 根据参数查询足迹
     * @param params memberId goodsId
     * @return
     */
    @Override
    public FootprintEntity getFootprintByParams(Map<String, Object> params) {
        Long memberId = params.get("userId") == null ? null: Long.valueOf((params.get("userId").toString()));
        Long goodsId = params.get("goodsId") == null ? null: Long.valueOf((params.get("goodsId").toString()));
        return baseMapper.selectOne(
                new QueryWrapper<FootprintEntity>()
                        .eq(memberId !=null, "member_id", memberId)
                        .eq(goodsId != null, "goods_id", goodsId));
    }
}
