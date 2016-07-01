package com.lanen.service.studyplan;

import java.util.List;

import com.lanen.base.BaseDao;
import com.lanen.model.clinicaltest.TestItem;
import com.lanen.model.studyplan.TblClinicalTestReq;
import com.lanen.model.studyplan.TblClinicalTestReqIndex;
import com.lanen.model.studyplan.TblClinicalTestReqIndex2;
import com.lanen.model.studyplan.TblStudyPlan;

public interface TblClinicalTestReqService extends BaseDao<TblClinicalTestReq> {

	/**
	 * 查询开始检查日期  在    beginDate与endDate之间的，并以开始检查日期 降序排序
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	List<TblClinicalTestReq> findByDate(String beginDate, String endDate);

	/**
	 * 通过课题编号，申请编号查询实体
	 * @param studyNo
	 * @param reqNo
	 * @return
	 */
	TblClinicalTestReq findByStudyNoAndReqNO(String studyNo, int reqNo);

	/**
	 * 查询有临检申请的   所有试验计划
	 * @return
	 */
	List<TblStudyPlan> findAllTblStudyPlan();

	/**
	 * 根据申请编号查询检验指标
	 * @param studyPlan
	 * @param reqNo
	 * @return
	 */
	List<TblClinicalTestReqIndex> getReqIndexByReqNo(TblStudyPlan studyPlan , int reqNo);
	
	/**
	 * 保存临检申请单
	 * @param tblClinicalTestReq
	 */
	void saveOrUpdateClinicalTestReq(TblClinicalTestReq tblClinicalTestReq , List<TblClinicalTestReqIndex>tblClinicalTestReqIndexList , 
			List<TblClinicalTestReqIndex2>tblClinicalTestReqIndex2List);
	
	/**
	 *根据试验计划查询临检申请 
	 * @param tblStudyPlan required
	 * @param beginDate/null
	 * @param endDate/null
	 * @return
	 */
	List<TblClinicalTestReq> getByStudyPlan(TblStudyPlan tblStudyPlan, String beginDate, String endDate);

	/**
	 * 保存 试验计划 、临检申请、以及检验指标和动物列表
	 * @param taskNo
	 * @param animalType
	 * @param clinicalTestDirector
	 * @param client
	 * @param animalIdList
	 * @param indexList
	 * @param isValidation 
	 */
	void saveStudyPlanAndClinicalTestReq(String taskNo, String animalType,
			String clinicalTestDirector, String client, List<String> animalIdList,
			List<TestItem> indexList, int isValidation);

	/**
	 * 根据年限查询临时任务的临检申请（包括外部临时，内部临时，内部临时转正）
	 * @param year
	 * @return
	 */
	List<TblClinicalTestReq> findTempReqWithyear(String year);

	/**
	 * 更新 试验计划 、临检申请、以及检验指标和动物列表
	 * @param taskNo
	 * @param animalType
	 * @param clinicalTestDirector
	 * @param client
	 * @param animalIdList
	 * @param indexList
	 * @param isValidation 
	 */
	void updateStudyPlanAndClinicalTestReq(String taskNo, String animalType,
			String clinicalTestDirector, String client, List<String> animalIdList,
			List<TestItem> indexList, int isValidation);
	/**
	 * 查询该申请下的最大     动物序号
	 * @param tblClinicalTestReq
	 * @return
	 */
	int getMaxAniSerialNum(TblClinicalTestReq tblClinicalTestReq) ;

	/**
	 * 通过StudyPlan查询临检申请List(已提交的，且按先父及其子，再父的顺序排序)   客户端lazy
	 * @param studyPlan
	 * @return
	 */
	List<TblClinicalTestReq> getSetByStudyPlan(TblStudyPlan studyPlan);

	/***
	 * 通过tblClinicalTestReq 查询临检申请-动物列表   客户端lazy
	 * @param tblClinicalTestReq
	 * @return
	 */
	List<TblClinicalTestReqIndex2> getTblClinicalTestReqIndex2ListByTblClinicalTestReq(
			TblClinicalTestReq tblClinicalTestReq);

	/**保存内部临时      临检申请
	 * @param tblStudyPlan
	 * @param animalIdList
	 * @param indexList
	 * @return
	 */
	int saveTempClinicalTestReq(TblStudyPlan tblStudyPlan, List<String> animalIdList,
			List<TestItem> indexList);

	/**编辑内部临时      临检申请
	 * @param tblClinicalTestReq
	 * @param animalIdList
	 * @param indexList
	 */
	void updateTempClinicalTestReq(TblClinicalTestReq tblClinicalTestReq,
			List<String> animalIdList, List<TestItem> indexList);
	
}
