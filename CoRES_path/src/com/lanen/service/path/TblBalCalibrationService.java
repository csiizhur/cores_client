package com.lanen.service.path;

import java.util.List;

import com.lanen.base.BaseDao;
import com.lanen.model.path.TblBalCalibration;
import com.lanen.model.path.TblBalCalibrationIndex;

public interface TblBalCalibrationService extends BaseDao<TblBalCalibration>{
	/**通过天平校准索引ID查找校准记录
	 * @param calIndexId
	 * @return
	 */
	List<TblBalCalibration> loadListByCalIndexID(String calIndexId);

	/**保存天平校准记录及天平校准记录索引
	 * @param list（天平校准记录）
	 * @param blBalCalibrationIndex（天平校准记录索引）
	 */
	void saveBalCalibration(List<TblBalCalibration> list,
			TblBalCalibrationIndex blBalCalibrationIndex);
}
