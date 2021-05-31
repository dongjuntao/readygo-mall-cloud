package com.mall.admin.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.admin.entity.MenuEntity;
import com.mall.admin.mapper.MenuMapper;
import com.mall.admin.service.AdminUserService;
import com.mall.admin.service.MenuService;
import com.mall.admin.service.RoleMenuService;
import com.mall.base.enums.MenuEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, MenuEntity> implements MenuService {

	@Autowired
	private AdminUserService adminUserService;
	@Autowired
	private RoleMenuService roleMenuService;
	@Autowired
	private MenuMapper menuMapper;

	@Override
	public List<MenuEntity> queryListParentId(Long parentId, List<Long> menuIdList) {
		List<MenuEntity> menuList = queryListParentId(parentId);
		if(menuIdList == null){
			return menuList;
		}

		List<MenuEntity> userMenuList = new ArrayList<>();
		for(MenuEntity menu : menuList){
			if(menuIdList.contains(menu.getId())){
				userMenuList.add(menu);
			}
		}
		return userMenuList;
	}

	@Override
	public List<MenuEntity> queryListParentId(Long parentId) {
		return baseMapper.queryListParentId(parentId);
	}

	@Override
	public List<MenuEntity> queryNotButtonList() {
		return baseMapper.queryNotButtonList();
	}

	@Override
	public List<MenuEntity> getUserMenuList(Long userId) {
		//系统管理员，拥有最高权限
		if(userId == 1){
			return getAllMenuList(null);
		}

		//用户菜单列表
		List<Long> menuIdList = adminUserService.queryAllMenuId(userId);
		return getAllMenuList(menuIdList);
	}

	@Override
	public void delete(Long menuId){
		//删除菜单
		this.removeById(menuId);
		//删除菜单与角色关联
		Map<String, Object> map = new HashMap<>();
		map.put("menu_id", menuId);
		roleMenuService.removeByMap(map);
	}

	/**
	 * 获取所有菜单列表
	 */
	private List<MenuEntity> getAllMenuList(List<Long> menuIdList){
		//查询根菜单列表
		List<MenuEntity> menuList = queryListParentId(0L, menuIdList);
		//递归获取子菜单
		getMenuTreeList(menuList, menuIdList);

		return menuList;
	}

	/**
	 * 递归
	 */
	private List<MenuEntity> getMenuTreeList(List<MenuEntity> menuList, List<Long> menuIdList){
		List<MenuEntity> subMenuList = new ArrayList<MenuEntity>();

		for(MenuEntity entity : menuList){
			//目录
			if(entity.getType() == MenuEnum.CATALOG.getValue()){
				entity.setList(getMenuTreeList(queryListParentId(entity.getId(), menuIdList), menuIdList));
			}
			subMenuList.add(entity);
		}
		return subMenuList;
	}
}
