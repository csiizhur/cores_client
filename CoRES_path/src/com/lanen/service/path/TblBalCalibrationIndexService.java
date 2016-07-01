package com.lanen.service.path;

import java.util.Date;
import java.util.List;

import com.lanen.base.BaseDao;
import com.lanen.model.path.TblBalCalibrationIndex;

public interface TblBalCalibrationIndexService extends BaseDao<TblBalCalibrationIndex>{
	/**根据校准开始时间，校准结束时间和天平编号来查询校准索引
	 * @param beginDate
	 * @param endDate
	 * @param balCode
	 * @return
	 */
	List<?>  loadListByCondition(Date beginDate,Date endDate,String balCode);

	/** 今天之内有没有校准记录
	 * @param balCode
	 * @return
	 */
	boolean isExistByBalCode(String balCode);

	/**获取该天平最后一次通过的校准记录索引id
	 * @param balCode
	 * @return
	 */
	String getPassCalId(String balCode);
}
