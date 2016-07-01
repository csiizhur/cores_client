package com.lanen.service.clinicaltest;

import com.lanen.base.BaseDao;
import com.lanen.model.clinicaltest.TblESLink;

public interface TblESLinkService extends BaseDao<TblESLink>{

	/**
	 * 根据实体类名和数据Id   查询实体
	 * @param string
	 * @param dataId
	 * @return
	 */
	TblESLink getByEntityNameAndDataId(String string, String dataId);
	/**
	 * 根据实体名，课题编号，签字类型，判断是否已签过字
	 * @param tableName
	 * @param studyNoPara
	 * @param esType
	 * @return
	 */
	int isESLink(String tableName, String studyNoPara, int esType);

}
