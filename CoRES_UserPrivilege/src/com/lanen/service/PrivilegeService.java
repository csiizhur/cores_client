package com.lanen.service;



import java.util.List;

import com.lanen.base.BaseDao;
import com.lanen.model.Module;
import com.lanen.model.Privilege;
import com.lanen.model.Role;

public interface PrivilegeService extends BaseDao<Privilege>{


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
	 * 保存实体（带时间和模块名称）
	 * @param obj
	 * @param moduleName
	 */
	void saveWithModuleAndTime(Privilege obj, String moduleName);

	/**
	 * 根据名称查询实体
	 * @param newValue
	 * @return
	 */
	Privilege getByName(String name);

	/**
	 * 除id以外实体是否存在该名称（name）
	 * @param name
	 * @param id
	 * @return
	 */
	boolean isNameExist(String name, String id);

	/**
	 * 跟新实体（带模块名称）
	 * @param obj
	 * @param moduleName
	 */
	void update(Privilege obj, String moduleName);

	/**
	 * 根据name删除entity
	 * @param value
	 */
	void deleteByName(String value);
	
	/**
	 * 根据权限名称列表  得到  权限列表
	 * @param privilegeNameList
	 * @return
	 */
	List<Privilege> getByPrivilegeNameList(List<String> privilegeNameList);

	/**根据模块，得到权限列表
	 * @param obj
	 * @return
	 */
	List<Privilege> getPrivilegeListByModule(Module obj);

	/**根据角色Id ，查询权限名称列表
	 * @param roleId
	 * @return
	 */
	List<String> getPrivilegeNameByRoleId(String roleId);

	/** 根据权限查询角色列表
	 * @param privilege
	 * @return
	 */
	List<Role> getRoleListByPrivilege(Privilege privilege);
}
