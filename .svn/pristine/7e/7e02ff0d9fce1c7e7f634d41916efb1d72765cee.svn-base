package com.lanen.service.quarantine.tblsession;

import java.util.List;

import com.lanen.base.BaseDao;
import com.lanen.model.quarantine.tblsession.TblBodyWeight;

public interface TblBodyWeightService extends BaseDao<TblBodyWeight>{

	/**
	 * 查询 动物id号，性别，体重，体重单位，称重时间 ，From TblSessionAnimals,TblBodyWeight 
	 * @param sessionId
	 * @return
	 */
	List<?> getListBySessionId(String sessionId);
	/**
	 * 保存或更新实体实体，实体暂无主键
	 * @param tblBodyWeight
	 * @return
	 */
	void saveOrUpdateNoId(TblBodyWeight tblBodyWeight);
	/**
	 * 根据 sessionId，animalId 查询实体
	 * @param sessionId
	 * @param animalId
	 * @return
	 */
	TblBodyWeight findBySessionIdAndAnimalId(String sessionId,String animalId);
	/**
	 * 查询实体列表，根据sessionId
	 * @param sessionId
	 * @return
	 */
	List<TblBodyWeight> getEntityListBySessionId(String sessionId);
	/**
	 * 查询课题编号列表（distinct     from  tblAnimalRecList     tblBodyWeight   动物Id号相等）
	 * @param recId
	 * @param sessionId
	 * @return
	 */
	List<String> findStudyNoListByRecIdSessionId(String recId, String sessionId);
	/**
	 * 查询   animalId,bodyWeight,weightUnit,devId（from  tblAnimalRecList     tblBodyWeight   动物Id号相等）
	 * @param recId
	 * @param sessionId
	 * @param studyNo
	 * @return
	 */
	List<?> findListByRecIdSessionIdStudyNo(String recId, String sessionId, String studyNo);
}
