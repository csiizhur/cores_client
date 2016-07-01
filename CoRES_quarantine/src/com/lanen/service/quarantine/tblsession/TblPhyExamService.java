package com.lanen.service.quarantine.tblsession;

import java.util.List;

import com.lanen.base.BaseDao;
import com.lanen.model.quarantine.tblsession.TblPhyExam;
import com.lanen.model.quarantine.tblsession.TblPhyExamResult;

public interface TblPhyExamService extends BaseDao<TblPhyExam>{

	/** animalId,gender ,bodyWeight,weightUnit,temperature,recordTime,examResult,id,room联合查询  tblSessionAnimals 和tblPhyExam 
	 * @param sessionId
	 * @return
	 */
	List<?> getListBySessionId(String sessionId);

	/**查询体检结果列表，根据sessionId,animalId  按orderNo 排序
	 * @param sessionId
	 * @param animalId
	 * @return
	 */
	List<TblPhyExamResult> getResultListBySessionIdAnimalId(String sessionId, String animalId);

	/**保存 动物体检  和体检结果列表  并返回主键
	 * @param tblPhyExam
	 * @param tblPhyExamResultList
	 * @return
	 */
	String saveAndResultList(TblPhyExam tblPhyExam, List<TblPhyExamResult> tblPhyExamResultList);

	/**更新动物体检  ，删除原体检结果列表 ，保存新体检结果列表
	 * @param tblPhyExam
	 * @param tblPhyExamResultList
	 */
	void updateAndResultList(TblPhyExam tblPhyExam, List<TblPhyExamResult> tblPhyExamResultList);

	/**查询实体列表，根据sessionId
	 * @param sessionId
	 * @return
	 */
	List<TblPhyExam> getEntityListBySessionId(String sessionId);

	/**是否存在        未体检动物
	 * @param sessionId
	 * @return
	 */
	boolean isExistNoExam(String sessionId);
	
}
