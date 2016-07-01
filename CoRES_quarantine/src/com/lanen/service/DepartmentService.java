package com.lanen.service;

import com.lanen.base.BaseDao;
import com.lanen.model.Department;

public interface DepartmentService extends BaseDao<Department> {

	/**
	 * 判断部门名称是否存在(除department_id之外的)
	 * @param department_name
	 * @return
	 */
	boolean isNameExist(String department_name, String department_id);

	/**
	 * 判断编号是否存在
	 */
	boolean isIdExist(String department_id);

	/**
	 * 判断部门名称是否存在
	 * @param department_name
	 * @return
	 */
	boolean isNameExist(String department_name);

	/**
	 * 根据名称删除实体
	 * @param item
	 * @return
	 */
	void deleteByName(String item);

	/**
	 * 根据名称查询实体
	 * @param item
	 * @return
	 */
	Department getByName(String item);

//	boolean isIdExist(String department_id);
//
//	boolean isNameExist(String department_name);
//
//	Department getByName(String item);
//
//	boolean isNameExist(String department_name, String department_id);
//
//	void deleteByName(String item);

}
