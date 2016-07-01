package com.lanen.service.quarantine.tblsession;

import java.util.List;

import com.lanen.base.BaseDao;
import com.lanen.model.quarantine.tblsession.TblSessionAnimals;

public interface TblSessionAnimalsService extends BaseDao<TblSessionAnimals> {

	/**
	 * 查询实体列表，根据sessionId
	 * @param sessionId
	 * @return
	 */
	List<TblSessionAnimals> getListBySessionId(String sessionId);

}
