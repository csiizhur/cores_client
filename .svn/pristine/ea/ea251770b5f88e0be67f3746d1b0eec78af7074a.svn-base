package com.lanen.service.quarantine.tblsession;

import java.util.List;

import com.lanen.base.BaseDao;
import com.lanen.model.quarantine.tblsession.TblAnimalDeath;

public interface TblAnimalDeathService extends BaseDao<TblAnimalDeath> {

	/**查询死亡原因   列表（去重）
	 * @return
	 */
	List<String> getDeadTypeList();

	/**保存或更新动物死亡登记，删除原死亡登记动物列表，保存新的动物列表
	 * @param tblAnimalDeath
	 * @param animalIdList
	 */
	void saveOrUpdate(TblAnimalDeath tblAnimalDeath, List<String> animalIdList);

	/**死亡登记动物Id号列表
	 * @param sessionId
	 * @return
	 */
	List<String> getAnimalIdListBySessionId(String sessionId);

	/**是否存在   死亡动物列表
	 * @param sessionId
	 * @return
	 */
	boolean isExistAnimal(String sessionId);

	/**保存或更新动物死亡登记
	 * @param tblAnimalDeath
	 */
	void saveOrUpdate(TblAnimalDeath tblAnimalDeath);

	/**查询    animalId,studyNo,room  from   tblDeadAniList ,tblAnimalRecList 
	 * @param sessionId
	 * @return
	 */
	java.util.List<?> getListBySessionId(String sessionId);

	/**在TblDeadAniList 中，删除   sessionId  下的  animalIdList
	 * @param sessionId
	 * @param animalIdList
	 */
	void deleteAnimalBySessionIdAnimalIdList(String sessionId, List<String> animalIdList);

}
