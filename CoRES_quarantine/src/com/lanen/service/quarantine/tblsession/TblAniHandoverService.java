package com.lanen.service.quarantine.tblsession;

import java.util.List;

import com.lanen.base.BaseDao;
import com.lanen.model.quarantine.tblsession.TblAniHandover;
import com.lanen.model.quarantine.tblsession.TblHandoverAniList;

public interface TblAniHandoverService extends BaseDao<TblAniHandover> {

	/**保存移交记录以及动物列表
	 * @param tblAniHandover
	 * @param tblHandoverAniListList
	 */
	void saveEntityAndList(TblAniHandover tblAniHandover,
			List<TblHandoverAniList> tblHandoverAniListList);

	/**查询动物移交列表
	 * @param sessionId
	 * @return
	 */
	List<TblHandoverAniList> getAniListBySessionId(String sessionId);
	
}
