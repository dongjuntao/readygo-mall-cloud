package com.mall.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.admin.entity.MenuEntity;

import java.util.List;



/**
 * @Author DongJunTao
 * @Description 菜单管理service
 * @Date 2021/5/28 13:33
 * @Version 1.0
 */
public interface MenuService extends IService<MenuEntity> {

	/**
	 * 根据父菜单，查询子菜单
	 * @param parentId 父菜单ID
	 * @param menuIdList  用户菜单ID
	 */
	List<MenuEntity> queryListParentId(Long parentId, List<Long> menuIdList);

	/**
	 * 根据父菜单，查询子菜单
	 * @param parentId 父菜单ID
	 */
	List<MenuEntity> queryListParentId(Long parentId);

	/**
	 * 获取不包含按钮的菜单列表
	 */
	List<MenuEntity> queryNotButtonList();

	/**
	 * 获取用户菜单列表
	 */
	List<MenuEntity> getUserMenuList(Long userId);

	/**
	 * 删除
	 */
	void delete(Long menuId);
}
