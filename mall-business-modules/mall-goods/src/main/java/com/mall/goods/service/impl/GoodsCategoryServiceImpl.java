package com.mall.goods.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.goods.entity.GoodsCategoryEntity;
import com.mall.goods.mapper.GoodsCategoryMapper;
import com.mall.goods.service.GoodsCategoryService;
import com.mall.goods.vo.GoodsCategoryInfoVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;

@Service("goodsCategoryService")
public class GoodsCategoryServiceImpl extends ServiceImpl<GoodsCategoryMapper, GoodsCategoryEntity>
		implements GoodsCategoryService {


	@Override
	public Map<String,List<GoodsCategoryEntity>> queryMergeGoodsCategoryTree(Long id) {
		List<GoodsCategoryEntity> categoryList = baseMapper.queryGoodsCategoryTree(id);
		List<GoodsCategoryEntity> mergeCategoryList = new ArrayList<>();
		Map<String,List<GoodsCategoryEntity>> categoryMap = new HashMap<>();
		Map<Integer, List<GoodsCategoryEntity>> goodsMap = new HashMap<>();
		for (GoodsCategoryEntity category : categoryList) {
			Integer groupNum = category.getGroupNum();
			if (goodsMap.get(groupNum) == null) {
				List<GoodsCategoryEntity> goodsList = new ArrayList<>();
				goodsList.add(category);
				goodsMap.put(groupNum, goodsList);
			} else {
				List<GoodsCategoryEntity> goodsList = goodsMap.get(groupNum);
				goodsList.add(category);
				goodsMap.put(groupNum, goodsList);
			}
		}
		for (Map.Entry<Integer, List<GoodsCategoryEntity>> map : goodsMap.entrySet()) {
			List<GoodsCategoryEntity> goodsCategoryList = map.getValue();
			List<GoodsCategoryEntity> mergeChildren = new ArrayList<>();
			GoodsCategoryEntity category = new GoodsCategoryEntity();
			List<GoodsCategoryInfoVO> goodsCategoryInfoList = new ArrayList<>();
			for (int i=0; i<goodsCategoryList.size(); i++) {
				GoodsCategoryInfoVO goodsCategoryInfoVO = new GoodsCategoryInfoVO();
				goodsCategoryInfoVO.setCategoryId(goodsCategoryList.get(i).getId());
				goodsCategoryInfoVO.setCategoryName(goodsCategoryList.get(i).getName());
				goodsCategoryInfoList.add(goodsCategoryInfoVO);
				List<GoodsCategoryEntity> children = goodsCategoryList.get(i).getChildren();
				for (int j=0; j<children.size(); j++) {
					mergeChildren.add(children.get(j));
				}
			}
			category.setGoodsCategoryInfoList(goodsCategoryInfoList);
			category.setChildren(mergeChildren);
			mergeCategoryList.add(category);
		}
		categoryMap.put("categoryList", categoryList);
		categoryMap.put("mergeCategoryList", mergeCategoryList);
		return categoryMap;
	}

	@Override
	public List<GoodsCategoryEntity> queryListParentId(Long parentId) {
		return baseMapper.queryListParentId(parentId);
	}

	@Override
	public List<GoodsCategoryEntity> queryGoodsCategoryTree(Long id) {
		return baseMapper.queryGoodsCategoryTree(id);
	}
}
