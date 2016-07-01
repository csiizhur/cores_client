package com.lanen.service.clinicaltest;


import java.util.List;

import com.lanen.base.BaseLongDao;
import com.lanen.model.clinicaltest.TblLog;

public interface TblLogService extends BaseLongDao<TblLog> {
	/**
	 * 查询某系统下的操作位置列表
	 * @param systemName
	 * @return
	 */
	List<String> getOperatHostListBySystemName(String systemName);
	
	/**
	 * 查询某系统下的日志列表
	 * @param beginDateStr  起始日期
	 * @param endDateStr    截止日期
	 * @param operatHost    操作主机
	 * @param systemName
	 * @return
	 */
	List<TblLog> getListByDateHostSystemName(String beginDateStr, String endDateStr,
			String operatHost, String systemName);
}
