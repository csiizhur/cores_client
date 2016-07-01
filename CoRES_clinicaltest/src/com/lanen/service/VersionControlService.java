package com.lanen.service;

import com.lanen.base.BaseDao;
import com.lanen.model.VersionControl;

public interface VersionControlService extends BaseDao<VersionControl> {
	/**
	 * 判断  版本是否可用
	 * @param systemName    系统名称
	 * @param version		版本
	 * @return
	 */
	boolean isValidVersion(String systemName,String version);

}
