package com.lanen.service.clinicaltest;

import java.util.List;

import com.lanen.base.BaseDao;
import com.lanen.model.clinicaltest.TblTrace;

public interface TblTraceService extends BaseDao<TblTrace>{

	
	/**
	 * 根据表名查询
	 * @param tableName
	 * @return
	 */
	List<TblTrace> getListByTableName(String tableName);
	/**
	 * 查询临检数据相关的  修改痕迹数据
	 * @param studyNo
	 * @param reqNo
	 * @param testItem
	 * @return
	 */
	List<TblTrace> getTblClinicalTestDataTraceListByStudyNoReqNoTestItem(String studyNo, int reqNo,
			int testItem);

}
