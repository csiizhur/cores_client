package com.lanen.service;

import com.lanen.base.BaseDao;
import com.lanen.model.Module;

public interface ModuleService extends BaseDao<Module> {

	/**
	 * 名称是否存在
	 * @param name
	 * @return
	 */
	boolean isIdNameExist(String name);

}
