package com.lanen.service.path;

import java.util.List;
import java.util.Map;

import com.lanen.base.BaseDao;
import com.lanen.jsonAndModel.Json;
import com.lanen.model.User;
import com.lanen.model.path.TblAnatomyReq;
import com.lanen.model.path.TblAnatomyReqAnimalList;
import com.lanen.model.path.TblAnatomyReqAttachedViscera;
import com.lanen.model.path.TblAnatomyReqPathPlanCheck;
import com.lanen.model.path.TblAnatomyReqVisceraWeigh;

/**
 * 动物解剖申请    service
 * @author 
 */
public interface TblAnatomyReqService extends BaseDao<TblAnatomyReq> {

	/**根据课题编号查询解剖申请及申请下的动物数量
	 * @param studyNoPara（课题编号）
	 * @return
	 */
	List<?> getListByStudyNo(String studyNoPara);

	/**根据课题编号获得申请编号（数据库中最大申请编号+1）
	 * @param studyNoPara（课题编号）
	 * @return
	 */
	Integer getReqNoByStudyNo(String studyNoPara);

	/**查询已提交的解剖申请，提交日期倒序.Map里字段有
	 *  submitFlag,id,reqNo,studyNo,submitter（src0129 ->李科）,submitDate,
	 * beginDate,endDate,animalType(专题里),animalNum（解剖动物数量），tempFlag,
	 * anatomyCheckFlag（需剖检标识）， histopathCheckFlag(需镜检标识),
	 * visceraWeighFlag(需脏器称重标识),visceraFixedFlag(需脏器固定标识),
	 * taskId(解剖任务)，anatomyCheckFinishSign(剖检完毕签字),histopathCheckFinishSign(镜检完毕/提交签字),
	 * visceraWeightFinishSign(脏器称重完毕签字),visceraFixedWeightFinishSign(脏器固定后称重完毕签字),
	 * ,histopathReviewRequirement(需复审标识)
	 * histopathReviewFinalSign(复审完最终签字)
	 * @param beginDateStr     yyyy-MM-dd
	 * @param endDateStr       yyyy-MM-dd  
	 * @return
	 */
	List<Map<String, Object>> getMapListBySubmitDate(String beginDateStr, String endDateStr);
	
	/**保存临时解剖申请和解剖任务，（剖检、称重、固定、镜检直接引用病理计划）,返回任务Id
	 * @param tblAnatomyReq（id，申请编号，4个标识还未设置，保存前设置）
	 * @param animalCodeList
	 */
	String saveTempReqAndAnatomyTask(TblAnatomyReq tblAnatomyReq, List<String> animalCodeList);

	/**新增解剖申请保存(包括保存解剖申请，以及对应的解剖申请-动物列表，解剖申请-脏器/组织学检查，
	 * 解剖申请-脏器称重，解剖申请-脏器称重-附加脏器)
	 * @param tblAnatomyReq(解剖申请)
	 * @param listAnimals(解剖申请-动物列表)
	 * @param listChecks(解剖申请-脏器/组织学检查)
	 * @param listWeighs(解剖申请-脏器称重)
	 * @param listAttachedVisceras(解剖申请-脏器称重-附加脏器)
	 */
	void addSave(TblAnatomyReq tblAnatomyReq,
			List<TblAnatomyReqAnimalList> listAnimals,
			List<TblAnatomyReqPathPlanCheck> listChecks,
			List<TblAnatomyReqVisceraWeigh> listWeighs,
			List<TblAnatomyReqAttachedViscera> listAttachedVisceras);

	/**编辑解剖申请保存(在编辑保存前，先删除该申请对应的原有的解剖申请-动物列表，解剖申请-脏器/组织学检查，
	 * 解剖申请-脏器称重，解剖申请-脏器称重-附加脏器，然后添加新的申请对应地的解剖申请-动物列表，解
	 * 剖申请-脏器/组织学检查，解剖申请-脏器称重，解剖申请-脏器称重-附加脏器)
	 * @param listOldAnimals(原有的解剖申请-动物列表)
	 * @param listOldChecks(原有的解剖申请-脏器/组织学检查)
	 * @param listOldWeighs(原有的解剖申请-脏器称重)
	 * @param listOldAttachedVisceras(原有的解剖申请-脏器称重-附加脏器)
	 * @param tblAnatomyReq(编辑后的解剖申请)
	 * @param listAnimals(新的解剖申请-动物列表)
	 * @param listChecks(新的解剖申请-脏器/组织学检查)
	 * @param listWeighs(新的解剖申请-脏器称重)
	 * @param listAttachedVisceras(新的解剖申请-脏器称重-附加脏器)
	 */
	void editSave(List<TblAnatomyReqAnimalList> listOldAnimals,
			List<TblAnatomyReqPathPlanCheck> listOldChecks,
			List<TblAnatomyReqVisceraWeigh> listOldWeighs,
			List<List<TblAnatomyReqAttachedViscera>> listOldAttachedVisceras,
			TblAnatomyReq tblAnatomyReq,
			List<TblAnatomyReqAnimalList> listAnimals,
			List<TblAnatomyReqPathPlanCheck> listChecks,
			List<TblAnatomyReqVisceraWeigh> listWeighs,
			List<TblAnatomyReqAttachedViscera> listAttachedVisceras);

	/**删除解剖申请及对应的解剖申请-动物列表，解剖申请-脏器/组织学检查，
	 * 解剖申请-脏器称重，解剖申请-脏器称重-附加脏器
	 * @param tblAnatomyReq(解剖申请)
	 * @param listAnimals(解剖申请-动物列表)
	 * @param listChecks(解剖申请-脏器/组织学检查)
	 * @param listWeighs(解剖申请-脏器称重)
	 * @param listAttachedVisceras(解剖申请-脏器称重-附加脏器)
	 */
	void deleteReqAndRelated(TblAnatomyReq tblAnatomyReq,
			List<TblAnatomyReqAnimalList> listAnimals,
			List<TblAnatomyReqPathPlanCheck> listChecks,
			List<TblAnatomyReqVisceraWeigh> listWeighs,
			List<List<TblAnatomyReqAttachedViscera>> listAttachedVisceras);

	/**   map (animal.groupID,animal.animalCode,animal.gender,dose.dosage,dose.femaleNum, 1 as endFlag)
	 * @param studyNo
	 * @param reqNo
	 * @return
	 */
	List<Map<String, Object>> getMapListByStudyNoReqNo(String studyNo,
			Integer reqNo);

	/**该动物是否已解剖或不存在
	 * @param studyNo
	 * @param animalStr
	 * @return
	 */
	boolean isHasAnatomyByStudyNoAnimalCode(String studyNo, String animalCode);

	/**查询病理专题负责人和病理负责人
	 * @param studyNo
	 * @return
	 */
	List<String> getPathSdCodeList(String studyNo);

	/**对应动物取消解剖（解剖前已死亡）
	 * @param studyNo
	 * @param animalCode
	 * @param user
	 * @param reason 
	 * @return 
	 */
	Json setCancel(String studyNo, String animalCode, User user, String reason);

	/**查询YYDB.dbo.TbANIMAL 动物是否都传出， A01:专题编号   A02：动物编号  A06 > 0传出
	 * @param studyNo
	 * @param animalCodeList
	 * @return
	 */
	Json isComeOut(String studyNo, List<String> animalCodeList);

}
