package com.lanen.service;

import java.util.List;
import java.util.Map;

import com.lanen.base.BaseLongDao;
import com.lanen.model.Employee;

public interface UserService extends BaseLongDao<Employee> {

	boolean isHasPrivilege(String employeeId, String privilegeName);
	/**
	 * 通过用户名密码 查询用户
	 * @param userName
	 * @param password
	 * @return
	 */
	Employee findUserByUserNamePassword(String userName, String password);
	/**
	 * 存取有权限登录的用户
	 * @param privilegeName
	 * @return
	 */
	List<Employee> findByPrivilegeName(String privilegeName);
	/**
	 * 查询该用户是否有权限
	 * @param name
	 * @param password
	 * @param pname
	 * @return
	 */
	Map<String,Object> checkPrivilege(String name,String password,String pname);
	/**
	 * 更新用户密码及修改密码时间，同时调用存储过程
	 * @param user
	 * @param password
	 */
	void updatePwd(Employee user,String newpassword);
	/**
     *  获取员工列表Map(String id)
     */
    List<Map<String, String>> getAllEmployeesMapNo();
    /**
	 * FX存放employee
	 */
	Map<String,Object> getAllEmployee();
}
