package com.lanen.service;

import java.util.List;

import com.lanen.base.BaseLongDao;
import com.lanen.model.UserRoleLog;

public interface UserRoleLogService extends BaseLongDao<UserRoleLog> {

	/**
	 * 保存日志列表
	 * @param userRoleLogList
	 */
	void saveList(List<UserRoleLog> userRoleLogList);
	
}
