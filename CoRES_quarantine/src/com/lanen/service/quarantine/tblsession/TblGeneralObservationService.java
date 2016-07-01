package com.lanen.service.quarantine.tblsession;

import java.util.List;

import com.lanen.base.BaseDao;
import com.lanen.model.quarantine.tblsession.TblGeneralObservation;

public interface TblGeneralObservationService extends BaseDao<TblGeneralObservation>{

	/**查询实体列表，根据sessionId
	 * @param sessionId
	 * @return
	 */
	List<TblGeneralObservation> getListBySessionId(String sessionId);

	/**
	 * 保存实体，暂无主键
	 * @param tblGeneralObservation
	 */
	String saveNoIdReturnId(TblGeneralObservation tblGeneralObservation);

	/**判断是否存在，根据  sessionId,animalId,     observation 或   结果为'正常'
	 * @param sessionId
	 * @param animalId
	 * @param observation
	 * @return
	 */
	boolean isExist(String sessionId, String animalId, String observation);

	/**判断是否存在未观察动物
	 * @param sessionId
	 * @return
	 */
	boolean isExistNoObservation(String sessionId);

	/** 设置剩下动物 为   无异常
	 * @param sessionId
	 */
	void observationOthers(String sessionId);

	/**
	 * 判断该会话下，该动物是否有观察结果
	 * @param sessionId
	 * @param animalId
	 * @return
	 */
	boolean isExist(String sessionId, String animalId);

	/**
	 * 查询课题编号列表（distinct from tblAnimalRecList tblGeneralObservation 动物Id号相等）
	 * @param recId
	 * @param sessionId
	 * @return
	 */
	List<String> findStudyNoListByRecIdSessionId(String recId, String sessionId);

	/**
	 * 查询  animalId，observation,  （from tblAnimalRecList tblGeneralObservation 动物Id号相等）
	 * @param recId
	 * @param sessionId
	 * @param studyNo
	 * @return
	 */
	List<?> findListByRecIdSessionIdStudyNo(String recId, String sessionId, String studyNo);


}
