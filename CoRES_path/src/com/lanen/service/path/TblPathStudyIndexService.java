package com.lanen.service.path;

import com.lanen.base.BaseDao;
import com.lanen.model.path.TblPathStudyIndex;

/**病理专题索引表Service
 * @author Administrator
 *
 */
public interface TblPathStudyIndexService extends BaseDao<TblPathStudyIndex> {

	/**根据专题编号查询实体
	 * @param studyNo
	 */
	public TblPathStudyIndex getByStudyNo(String studyNo);
}
