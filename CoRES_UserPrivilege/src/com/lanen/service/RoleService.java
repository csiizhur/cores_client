package com.lanen.service;

import java.util.List;

import com.lanen.base.BaseDao;
import com.lanen.model.Privilege;
import com.lanen.model.Role;
import com.lanen.model.User;

public interface RoleService extends BaseDao<Role>{

	/**
	 * 保存实体，初始化创建时间
	 * @param role
	 */
	void saveWithTime(Role role);

	/**
	 * 根据名称查询实体
	 * @param roleName
	 * @return
	 */
	Role findByRoleName(String roleName);
	
	/**
	 * 通过角色名称 得到角色列表
	 * @param editDeskList
	 * @return
	 */
	List<Role> findListByRoleNames(List<String> editDeskList);

	/**
	 * 判断id是否存在
	 * @param id
	 * @return
	 */
	boolean isIdExist(String id);

	/**
	 * 判断name是否存在
	 * @param name
	 * @return
	 */
	boolean isNameExist(String name);

	/**
	 * 保存是entity  ，带    系统名称和权限列表
	 * @param role
	 * @param systemName
	 * @param privilegeNameList
	 */
	void saveWithModuleAndTimeAndPrivilege(Role role, String systemName, List<String> privilegeNameList);

	/**
	 * 除id以外实体是否存在该名称（name）
	 * @param name
	 * @param id
	 * @return
	 */
	boolean isNameExist(String name, String id);

	/**
	 * 跟新entity  带权限名称列表
	 * @param role
	 * @param privilegeNameList
	 */
	void updateWithPrivilege(Role role, List<String> privilegeNameList);

	/**
	 * 根据角色名称列表得到角色列表
	 * @param roleNameList
	 * @return
	 */
	List<Role> getRoleListByRoleNameList(List<String> roleNameList);

	/**根据系统id 查询角色列表
	 * @return
	 */
	List<Role> findRoleListBySystemId_1(String systemId);

	/** 根据用户Id 查询角色列表
	 * @param id
	 * @return
	 */
	List<Role> findRoleListByUserId_1(String id);

	/** 根据角色查询用户列表
	 * @param obj
	 * @return
	 */
	List<User> getUserListByRole(Role obj);

	
	

}
