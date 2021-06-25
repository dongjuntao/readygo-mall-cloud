package com.mall.goods.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.goods.entity.GoodsCategoryEntity;
import com.mall.goods.mapper.GoodsCategoryMapper;
import com.mall.goods.service.GoodsCategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("goodsCategoryService")
public class GoodsCategoryServiceImpl extends ServiceImpl<GoodsCategoryMapper, GoodsCategoryEntity>
		implements GoodsCategoryService {


	@Override
	public List<GoodsCategoryEntity> queryGoodsCategoryTree(Long id) {
		return baseMapper.queryGoodsCategoryTree(id);
	}

	@Override
	public List<GoodsCategoryEntity> queryListParentId(Long parentId) {
		return baseMapper.queryListParentId(parentId);
	}
}
