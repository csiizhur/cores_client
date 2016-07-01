package com.lanen.service.quarantine.tblsession;

import java.util.List;

import com.lanen.base.BaseDao;
import com.lanen.model.quarantine.tblsession.TblAniResite;

public interface TblAniResiteService extends BaseDao<TblAniResite>{

	/**保存重新安置记录及动物列表
	 * @param tblAniResite
	 * @param animalIdList
	 */
	void saveEntityAndList(TblAniResite tblAniResite, List<String> animalIdList);

	/**在tblResiteAniList 在查询动物id号列表
	 * @param sessionId
	 * @return
	 */
	List<String> getAnimalIdListBySessionId(String sessionId);

}
