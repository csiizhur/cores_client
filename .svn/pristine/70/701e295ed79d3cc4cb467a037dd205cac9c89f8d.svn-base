package com.lanen.service.path;

import java.util.List;
import java.util.Map;

import com.lanen.base.BaseDao;
import com.lanen.jsonAndModel.Json;
import com.lanen.model.path.TblHistopathCheck;
import com.lanen.model.path.TblPathStudyIndex;
import com.lanen.model.path.TblTissueSliceIndex;
import com.lanen.model.path.TblTissueSliceViscera;

/**镜检service
 * @author Administrator
 *
 */
public interface TblHistopathCheckService extends BaseDao<TblHistopathCheck> {


	/**查询待镜检动物根据专题编号和动物编号列表(animalCode,gender,isNoAbnormal,resultNum)
	 * @param studyNo
	 * @param animalCodeList
	 * @return
	 */
	List<Map<String, Object>> getAnimalByStudyNoAnimalCodeList(String studyNo,
			List<String> animalCodeList);
	
	/**查询待镜检动物(animalCode,gender,isNoAbnormal,resultNum)
	 * @param tblTissueSliceIndex
	 * @return
	 */
	@Deprecated
	List<Map<String, Object>> getAnimalByTissueSliceIndex(TblTissueSliceIndex tblTissueSliceIndex);

	/** 根据切片序号Id列表动物编号，查询切片编号及脏器、组织（异常组织取材编号），从组织取材排次中查询，性别相匹配
	 * sliceVisceraId,animalCode,visceraOrTissueName,visceraType,visceraCode,visceraFixedRecordId,"+
	 *	visceraName,subVisceraCode,subVisceraName,"+
	 *	specialFlag,anatomyPosCode,anatomyPos,anatomyFindingCode,"+
	 *	anatomyFindingFlag,anatomyFingding,bodySurfacePos,sueSliceVisceraRecordId_hascheck（变蓝）,anatomyfinding(变绿)
	 * @param tissueSliceSnIdList
	 * @param studyNo
	 * @param animalCode
	 * @param gender
	 * @return
	 */
	List<Map<String, Object>> getSliceCodeVisceraByTissueSliceSnIdListAnimalCode(
			List<String> tissueSliceSnIdList, String studyNo, String animalCode, Integer gender);
	
	/** 根据切片编号索引，查询切片编号及脏器、组织（异常组织取材编号）,动物对应脏器未自溶或缺失，性别也匹配
	 * sliceVisceraId,animalCode,visceraOrTissueName,visceraType,visceraCode,visceraFixedRecordId,"+
	 *	visceraName,subVisceraCode,subVisceraName,"+
	 *	specialFlag,anatomyPosCode,anatomyPos,anatomyFindingCode,"+
	 *	anatomyFindingFlag,anatomyFingding,bodySurfacePos,sueSliceVisceraRecordId_hascheck
	 * @param tblTissueSliceIndex
	 * @param animalCode
	 * @param gender
	 * @return
	 */
	@Deprecated
	List<Map<String, Object>> getSliceCodeVisceraByTissueSliceIndex(
			TblTissueSliceIndex tblTissueSliceIndex, String animalCode, Integer gender);

	/**记录不存在通过，存在，返回 msg  ‘1’：已存在未见异常    0:已登记异常
	 * @param studyNo
	 * @param animalCode
	 * @param sliceVisceraId
	 * @param subVisceraCode 
	 * @param visceraCode 
	 * @return
	 */
	Json checkRecord(String studyNo, String animalCode, String sliceVisceraId, String visceraCode, String subVisceraCode);

	/**保存，id,operateTime 在服务器端设置
	 * @param tblHistopathCheck
	 */
	void saveOne(TblHistopathCheck tblHistopathCheck);

	/**查询 镜检结果列表（TblHistopathCheck），
	 * @param studyNo
	 * @param animalCode
	 * @param sliceVisceraId
	 * @return
	 */
	List<TblHistopathCheck> getListByStudyNoAnimalCodeSliceVisceraId(String studyNo,
			String animalCode, String sliceVisceraId);
	/**查询 镜检结果列表（TblHistopathCheck）(含复查完成后删除的 ，即historyFlag == 2)，
	 * @param studyNo
	 * @param animalCode
	 * @param sliceVisceraId
	 * @return
	 */
	List<TblHistopathCheck> getListByStudyNoAnimalCodeSliceVisceraId2(String studyNo,
			String animalCode, String sliceVisceraId);

	/**查询该专题下的组织学检查，
	 * histopathCheck.animalCode,histopathCheck.tissueSliceVisceraRecordId,histopathCheck.isNoAbnormal,histopathCheck.tumorFlag,
		 * histopathCheck.metastasisFlag,histopathCheck.histoPos,histopathCheck.lesionFinding,histopathCheck.primaryViscera,
		 * histopathCheck.primaryTumor,convert(varchar(10),histopathCheck.operateTime,120) as operateTime,visceraOrTissueName,
		 * id,histopathReviewOpinion,historyFlag
	 * @param studyNo
	 * @param sortMethod  1.动物  ，2.脏器
	 * @return
	 */
	List<Map<String, Object>> getMapListByStudyNoSortMethord(String studyNo, int sortMethod);

	/**查询源发脏器肿瘤列表，（当前动物的，其他脏器的肿瘤）
	 * @param studyNo
	 * @param animalCode
	 * @param visceraCode
	 * @return
	 */
	List<Map<String, Object>> getMapListByStudyNoAnimalCodeVisceraCode(String studyNo,
			String animalCode, String visceraCode);

	/**查询参照所见列表（其他动物的当前脏器）
	 * @param studyNo
	 * @param animalCode
	 * @param visceraCode
	 * @return
	 */
	List<Map<String, Object>> getReferMapListByStudyNoAnimalCodeVisceraCode(String studyNo,
			String animalCode, String visceraCode);

	/**保存参照所见
	 * @param referId
	 * @param tblHistopathCheck
	 */
	void saveOne(String referId, TblHistopathCheck tblHistopathCheck);

	/**
	 * 更新使用频度
	 */
	void updateFreqCount();

	/**查询临检数据(testIndex,testIndexAbbr,testData,testIndexUnit)
	 * @param studyNo
	 * @param animalCode
	 * @param testItem
	 * @param testDate yyyy-MM-dd（检验日期）
	 * @return
	 */
	List<Map<String, Object>> getClinicalDataByStudyNoAnimalCodeTestItemTestDate(String studyNo,
			String animalCode, Integer testItem, String testDate);

	/**查询临检数据（testItem,testDate(yyyy-MM-dd)）去重,日期升序排列
	 * @param studyNo
	 * @param animalCode
	 * @return
	 */
	List<Map<String, Object>> getClinicalDataByStudyNoAnimalCodeTestItemTestDate(String studyNo,
			String animalCode);

	/**删除镜检结果（置为历史记录），并保存签字(改为不签字了)
	 * @param id
	 * @param reason
	 * @param operator
	 * @return
	 */
	Json deleteOne(String id, String reason, String operator);
	
	/**删除镜检结果（置为历史记录，复查后删除），并保存签字
	 * @param id
	 * @param reason
	 * @param operator
	 * @return
	 */
	Json deleteOne2(String id, String reason, String operator);

	/**镜检完成前检查（已组织取材的必须全部完成）
	 * @param studyNo
	 * @return
	 */
	Json checkFinishCheck(String studyNo);

	/**镜检完成签字（ histopathCheckFinishSign
	 * 			,histopathReviewFlag(0：未提交，1：提交，2：完成复审，3：完成复审修改（最终确认）)）
	 * @param tblPathStudyIndex 无Id
	 * @param operator
	 */
	void checkFinishSign(TblPathStudyIndex tblPathStudyIndex, String operator);

	/**填写或更新复查意见
	 * @param id
	 * @param opinion
	 */
	void reviewOpinion(String id, String opinion);

	/**复核完成
	 * @param studyNo
	 * @param operator
	 */
	void reviewFinishSign(String studyNo, String operator);

	/**查询  studyNo,animlaCode,visceraCode,
	 * @param id
	 * @return
	 */
	Map<String, Object> getStudyNoAnimalCodeVisceraCodeById(String id);

	/**最终签字，更新状态      histopathReviewFinalSign,histopathReviewFlag(0：未提交，1：提交，2：完成复审，3：完成复审修改（最终确认）)
	 * @param studyNo
	 * @param operator
	 */
	void reviewFinalSign(String studyNo, String operator);

	/**最终签字前检查
	 * @param studyNo
	 * @return
	 */
	Json finalSignCheck(String studyNo);

	/**判断当前用户是否有进入 镜检 权限 
	 * （1、病理负责人，可以     
	 * 2、病理专题负责人，对应专题可以    ，
	 * 3、同行评议人     ，对应任务且该任务镜检已提交 可以）
	 * @param userId
	 * @param studyNo
	 * @return
	 */
	boolean isHasHistopathPriviege(String userId, String studyNo);

	/**判断当前用户是  1.病理专题负责人或病理负责人    2.同行评议人
	 * @param studyNo
	 * @param userName
	 * @return
	 */
	int getUserFlag(String studyNo, String userName);

	/**展示打印结果（最终结果）
	 * @param studyNo
	 * @param sortType 1:动物优先   2：脏器优先
	 * @return
	 */
	List<Map<String, Object>> getPrintResultMapListByStudyNo(String studyNo,int sortType);
	
	/**展示打印结果（镜检记录）
	 * @param studyNo
	 * @param sortType 1:动物优先   2：脏器优先
	 * @return
	 */
	List<Map<String, Object>> getPrintResultMapListByStudyNo_1(String studyNo,int sortType);
	
	/**展示打印结果（复核意见）
	 * @param studyNo
	 * @param sortType 1:动物优先   2：脏器优先
	 * @return
	 */
	List<Map<String, Object>> getPrintResultMapListByStudyNo_2(String studyNo,int sortType);
	
	/**展示打印结果（修改记录）
	 * @param studyNo
	 * @param sortType 1:动物优先   2：脏器优先
	 * @return
	 */
	List<Map<String, Object>> getPrintResultMapListByStudyNo_3(String studyNo,int sortType);

	/**镜检Id，lesionFinding，致死原因使用
	 * @param studyNo
	 * @param animalCode
	 * @return
	 */
	List<Map<String, Object>> getMapListForDeathReason(String studyNo, String animalCode);

	/** 根据切片序号Id列表，查询切片编号及脏器、组织（异常组织取材编号），从组织取材排次中查询
	 * sliceVisceraId,animalCode,visceraOrTissueName,visceraType,visceraCode,visceraFixedRecordId,"+
	 *	visceraName,subVisceraCode,subVisceraName,"+
	 *	specialFlag,anatomyPosCode,anatomyPos,anatomyFindingCode,"+
	 *	anatomyFindingFlag,anatomyFingding,bodySurfacePos
	 * @param tissueSliceSnIdList
	 * @param studyNo
	 * @return
	 */
	List<Map<String, Object>> getSliceCodeVisceraByTissueSliceSnIdList(
			List<String> tissueSliceSnIdList, String studyNo);
	
	/**查询待镜检动物根据专题编号和动物编号列表已及切片脏器Id(animalCode,gender,isNoAbnormal,resultNum)
	 * @param studyNo
	 * @param animalCodeList
	 * @param sliceVisceraId
	 * @return
	 */
	List<Map<String, Object>> getAnimalByStudyNoAnimalCodeListSliceVisceraId(String studyNo,
			List<String> animalCodeList, String sliceVisceraId);

	/**查询所有切片、动物、切片脏器Id，(map :sliceCode,animalCode,sliceVisceraId)排除已排除的，性别不符的
	 *   order by  sliceCode,animalCode,sliceVisceraId
	 * @param studyNo 
	 * @param animalCodeList
	 * @param tissueSliceSnIdList
	 * @return
	 */
	List<Map<String, Object>> getAllSliceCodeAnimalCodeSliceViscersId(String studyNo, List<String> animalCodeList,
			List<String> tissueSliceSnIdList);

	/**查询组织取材编号脏器表Id（补录的数据）
	 * @param studyNo
	 * @param animalCode
	 * @param visceraCode
	 * @param subVisceraCode
	 * @param anatomyFindingCode
	 * @param bodySurfacePos
	 * @return
	 */
	String getSliceVisceraId(String studyNo, String animalCode, String visceraCode,
			String subVisceraCode, String anatomyFindingCode, String bodySurfacePos);

	/**保存手工录入数据（无sliceVisceraId的情况）,返回 sliceVisceraId
	 * @param tblTissueSliceViscera 无id
	 * @param tblHistopathCheck id，operateTime，sliceVisceraId
	 */
	String saveHandWordRecord(TblTissueSliceViscera tblTissueSliceViscera,
			TblHistopathCheck tblHistopathCheck);

	/**参照所见保存，无sliceVsiceraId
	 * @param referId
	 * @param tblHistopathCheck  id，operateTime，sliceVisceraId及参照方面的内容
	 * @param tblTissueSliceViscera 无id
	 * @return
	 */
	String saveOne(String referId, TblHistopathCheck tblHistopathCheck,
			TblTissueSliceViscera tblTissueSliceViscera);

	/**查询待镜检动物根据专题编号(animalCode,gender,isNoAbnormal,resultNum),已镜检的
	 * @param studyNo
	 * @return
	 */
	List<Map<String, Object>> getAnimalByStudyNo(String studyNo);

	/**获取当前已动物的已镜检脏器或组织列表,map:animalCode,sliceVisceraId,visceraType,visceraCode,visceraFixedRecordId,visceraOrTissueName,
	 *		visceraName,subVisceraCode,subVisceraName,
	 *			specialFlag,anatomyPosCode,anatomyPos,anatomyFindingCode,
	 *		anatomyFindingFlag,anatomyFingding,bodySurfacePos,sn
	 * @param studyNo
	 * @param animalCode
	 * @param gender
	 * @return
	 */
	List<Map<String, Object>> getTissueSliceViscera(String studyNo, String animalCode);

	/**记录不存在通过，存在，返回 msg  ‘1’：已存在未见异常    0:已登记异常
	 * @param studyNo
	 * @param animalCode
	 * @param visceraCode
	 * @param subVisceraCode
	 * @param anatomyFindingCode
	 * @param bodySurfacePos
	 * @return
	 */
	Json checkRecord(String studyNo, String animalCode, String visceraCode, String subVisceraCode,
			String anatomyFindingCode, String bodySurfacePos);

	/**查询镜检结果（未删除的）
	 * @param studyNo
	 * @param animalCode
	 * @param visceraCode
	 * @param subVisceraCode
	 * @param anatomyFindingCode
	 * @param bodySurfacePos
	 * @return
	 */
	List<TblHistopathCheck> getListByStudyNoAnimalCodeSliceViscera(String studyNo,
			String animalCode, String visceraCode, String subVisceraCode,
			String anatomyFindingCode, String bodySurfacePos);

	/**缺失登记，且签字
	 * @param tblHistopathCheck（主键没有，）
	 */
	void missingRegister(TblHistopathCheck tblHistopathCheck);

	/**缺失登记，且签字（无sliceVisceraId的情况）,返回 sliceVisceraId
	 * @param tblTissueSliceViscera
	 * @param tblHistopathCheck
	 * @return
	 */
	String saveHandWordRecordAndMissingRegister(TblTissueSliceViscera tblTissueSliceViscera,
			TblHistopathCheck tblHistopathCheck);

	/**判断是否已登记缺失
	 * @param studyNo
	 * @param animalCode
	 * @param visceraCode
	 * @param subVisceraCode
	 * @return
	 */
	boolean isRegisterMissing(String studyNo, String animalCode, String visceraCode,
			String subVisceraCode);

}
