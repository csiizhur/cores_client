package com.lanen.service.quarantine.tblsession;

import java.util.List;

import com.lanen.base.BaseDao;
import com.lanen.model.quarantine.tblsession.TblSession;
import com.lanen.model.quarantine.tblsession.TblSessionAnimals;

public interface TblSessionService extends BaseDao<TblSession> {

	/**
	 * 保存会话（还未分配主键），及动物列表   并返回sessionId
	 * @param tblSession
	 * @param tblSessionAnimalsList
	 */
	String saveTblSessionAndAnimalList(TblSession tblSession,
			List<TblSessionAnimals> tblSessionAnimalsList);
	
	/**
	 * 查询数据  根据时间，会话种类，动物种类，课题编号
	 * @param startDateStr
	 * @param endDateStr
	 * @param sessionType
	 * @param animalTypeStr
	 * @param studyNoStr
	 * @return
	 */ 
	List<TblSession> findByDateAnimalStudyNo(String startDateStr, String endDateStr, String sessionType ,String animalTypeStr,
			String studyNoStr);
	
	/**
	 * 查询课题列表
	 * @return
	 */
	List<String> findStudyNoList(String sessionType);
	
	/**
	 * 保存签字，更新tblSession 的signId   ，endTime 并返回signId
	 * @param sessionId
	 * @param i			//签字类型  
	 * @param realName
	 * @param esTypeDesc
	 * @return
	 */
	String sign(String sessionId, int esType, String realName,String esTypeDesc);

	/**
	 * 保存签字，更新tblSession 的checkId   返回checkId
	 * @param sessionId
	 * @param i			//签字类型  
	 * @param realName
	 * @param esTypeDesc
	 * @return
	 */
	String check(String sessionId, int i, String realName, String esTypeDesc);

	/**
	 * 称重任务是否完成（会话动物树是否与称重动物树相等）
	 * @param sessionId
	 * @return
	 */
	boolean isFinishAboutBodyWeight(String sessionId);


}
