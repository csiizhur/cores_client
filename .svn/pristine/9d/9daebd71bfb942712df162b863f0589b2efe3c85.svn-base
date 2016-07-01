package com.lanen.service;

import java.util.List;

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
	/**
	 * 根据名称查询实体(users == null)
	 * @param item
	 * @return
	 */
	Department getByName_1(String item);

	/** 查询所有部门名称名称
	 * @return
	 */
	List<String> findAllName_1();

	/**查询所有部门实体（users == null）
	 * @return
	 */
	List<Department> findAll_1();

}
