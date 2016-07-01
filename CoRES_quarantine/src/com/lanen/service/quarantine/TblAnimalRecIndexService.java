package com.lanen.service.quarantine;

import java.util.List;
import java.util.Set;

import com.lanen.base.BaseDao;
import com.lanen.model.clinicaltest.TblLog;
import com.lanen.model.quarantine.TblAnimalRecIndex;

public interface TblAnimalRecIndexService extends BaseDao<TblAnimalRecIndex>{

	/**
	 * 保存接受登记单(及对应动物列表)，并把接收单号保存到TblQRRequestStudyNo中
	 * @param tblAnimalRecIndex
	 * @param qrrIdList
	 */
	String saveAndUpdateTblQRRequestStudyNo(TblAnimalRecIndex tblAnimalRecIndex,
			List<String> qrrIdList);
	
	/**
	 * 查询数据  根据时间，动物
	 * @param startDateStr
	 * @param endDateStr
	 * @param animalTypeStr
	 * @param animalStrainStr
	 * @return
	 */
	List<TblAnimalRecIndex> findByDateAnimal(String startDateStr, String endDateStr, String animalTypeStr,
			String animalStrainStr);

	/**
	 * 更新接受登记单(及对应动物列表)，并把接收单号更新到TblQRRequestStudyNo中
	 * @param tblAnimalRecIndex
	 * @param qrrIdList
	 */
	void UpdateOneselfAndTblQRRequestStudyNo(TblAnimalRecIndex tblAnimalRecIndex,
			List<String> qrrIdList);

	/**
	 * 更新接收单中的  SignId字段,保存日志和电子签字并返回signId
	 * @param tblLog
	 * @param recId 
	 * @return
	 */
	String updateSignIdAndSaveTblESAndTblLog(TblLog tblLog, String recId);

	/**
	 * 更新接收单中的 CheckId字段,保存日志和电子签字并返回signId
	 * @param tblLog
	 * @param recId 
	 * @return
	 */
	String updateCheckIdAndSaveTblESAndTblLog(TblLog tblLog, String recId);
	/**
	 * 更新接收单中的 CheckId字段和申请单中的PlanState字段,保存日志和电子签字并返回signId
	 * @param tblLog
	 * @param recId
	 * @param studyNoSet
	 * @return
	 */
	String updateSignIdPlanStateAndSaveTblESAndTblLog(TblLog tblLog, String recId,
			Set<String> studyNoSet);

	/**
	 * 查询接收单号列表 ，通过动物种类品系级别，（且是已复核的接收单）
	 * @param animalType
	 * @param animalStrain
	 * @param animalLevel
	 * @return
	 */
	List<String> getRecIdListByAnimal(String animalType, String animalStrain, String animalLevel);

	/**
	 * 查询接收单号列表 ，通过动物种类.（且是已复核的接收单,对动物列表里有   未死亡，未移交，未备库）
	 * @param animalType
	 * @return
	 */
	List<String> getRecIdListByAnimal(String animalType);

	/**查询动物种类，根据接收单号
	 * @param recId
	 * @return
	 */
	String getAnimalTypeByRecId(String recId);

}
