package com.lanen.service;

import java.util.List;
import java.util.Map;

import com.lanen.base.BaseDao;
import com.lanen.model.User;

public interface UserService extends BaseDao<User> {

	/**
	 * 通过用户名密码 查询用户
	 * @param userName
	 * @param password
	 * @return
	 */
	User findUserByUserNamePassword(String userName, String password);

//	/**
//	 * 通过用户Id 获得该user 的全部权限     控件名称列表
//	 * @param id
//	 */
//	List<String> getPrivilegesByuserId(String id);
	
	
	/**
	 * 根据用户编号查询实体
	 * @param moduleName
	 * @return
	 */
	public User findByUserCode(String userCode);

	/**
	 * 判断id是否存在
	 * @param id
	 * @return
	 */
	boolean isIdExist(String id);

	/**
	 * 判断userName是否存在
	 * @param id
	 * @return
	 */
	boolean isNameExist(String userName);

	/**
	 * 保存实体，带部门，角色列表
	 * @param entity
	 * @param departmentName
	 * @param roleNameList
	 */
	void saveWithDepartmentAndTimeAndRole(User entity, String departmentName,
			List<String> roleNameList);

	/**
	 * 根据名称查询实体
	 * @param value
	 * @return
	 */
	User getByUserName(String userName);

	/**
	 * 跟新实体，带权限名称列表
	 * @param trim
	 * @param roleNameList
	 * @param user 
	 */
	void update(String id, List<String> roleNameList, User user);

	/**
	 * 账号停用
	 * @param id
	 * @param string
	 */
	void stopId(String id, String string);

	/**
	 * 密码重置
	 * @param userName
	 */
	void resetPassword(String userName);

	/**
	 * 账号停用
	 * @param userName
	 * @param string
	 */
	void stopUserName(String userName, String remark);

	/**
	 * 更新用户，包括部门
	 * @param entity
	 * @param departmentName
	 */
	void update(User entity, String departmentName);

	/**
	 * 更新实体（需要重新设定最后修改密码时间）
	 * @param user
	 */
	void updateWithTime(User user);


	/**
	 * 根据权限名称查询有该权限的所有用户
	 * @param privilegeName
	 * @return
	 */
	List<User> findByPrivilegeName(String privilegeName);

	/**
	 * 根据用户名  、密码、和权限名称判断用户是否有权限
	 * @param userName
	 * @param password
	 * @param privielgeName
	 * @return
	 */
	Map<String, Object> checkPrivilege(String userName, String password, String privielgeName);
	
	/**
	 * 更新用户密码及修改密码时间，同时调用存储过程
	 * @param user
	 * @param password
	 */
	void updatePwd(User user,String password);
}
