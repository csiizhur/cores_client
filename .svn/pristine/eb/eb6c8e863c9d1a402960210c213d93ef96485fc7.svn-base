package com.lanen.service.clinicaltest;

import java.util.List;

import com.lanen.base.BaseDao;
import com.lanen.model.clinicaltest.TblTrace;

public interface TblTraceService extends BaseDao<TblTrace>{
	

	/**
	 * 保存修改痕迹列表
	 * @param tblTraceList
	 */
	void saveList(List<TblTrace> tblTraceList);

	/**根据表名和数据id查询修改痕迹列表
	 * @param string
	 * @param sessionId
	 * @return
	 */
	List<TblTrace> getListByTableNameAndDataId(String string, String sessionId);
}
