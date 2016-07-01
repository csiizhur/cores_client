package com.lanen.service.path;

import java.util.List;
import java.util.Map;

import com.lanen.base.BaseDao;
import com.lanen.model.path.TblTissueSliceIndex;
import com.lanen.model.path.TblTissueSliceSn;
import com.lanen.model.path.TblTissueSliceViscera;

/**组织切片编号索引 Service
 * 
 * @author Administrator
 *
 */
public interface TblTissueSliceIndexService extends BaseDao<TblTissueSliceIndex>{

	/** 查询该任务下已固定脏器列表及其子脏器，Map
	 * @param taskId
	 * @return
	 */
	List<Map<String, Object>> getFiexdVisceraMapList(String taskId);

	/** taskId,sliceCodeType,operatorSign,gender,groupId ，根据任务Id号及切片编号类型(类型仅限 0)
	 * @param taskId
	 * @param sliceCodeType
	 * @return
	 */
	List<Map<String, Object>> getMapListByTaskIdSliceCodeType(String taskId, int sliceCodeType);
	
	/**根据studyNo 查询  组织切片编号索引表 实体（仅限常规组织取材编号）
	 * @param studyNo
	 * @return
	 */
	TblTissueSliceIndex getByStudyNo(String studyNo);

	/**查询 sliceCode,visceraType,visceraCode,visceraName,subVisceraCode,subVisceraName,sn,subSn,gender    ， 根据任务Id号,类型仅限0（常规组织取材编号）
	 * @param taskId
	 * @return
	 */
	List<Map<String, Object>> getSliceSnVisceraMapListByTaskId(String taskId);

	/**查询专题对应病理计划的脏器列表，若无病理计划，则  查询解剖申请的脏器列表的合集（剖检 或镜检），
	 *  visceraType,visceraCode,visceraName,subVisceraCode,subVisceraName,sn,subSn
	 * @param studyNo
	 * @param showHistopathViscera   true:仅查询需镜检脏器  false :查询需剖检脏器
	 * @return
	 */
	List<Map<String, Object>> getVisceraMapListByStudyNo(String studyNo, boolean showHistopathViscera);

	/**保存切片 索引，编号，脏器(sliceCodeType == 0 or 2)
	 * @param tblTissueSliceIndex（无主键及时间）
	 * @param sliceCodeList
	 * @param visceraMapList
	 * @return 
	 */
	TblTissueSliceIndex addOneFor02(TblTissueSliceIndex tblTissueSliceIndex,
			List<String> sliceCodeList, List<Map<String, Object>> visceraMapList);

	/**更新切片 索引，编号，脏器(sliceCodeType == 0 or 2)
	 * @param tblTissueSliceIndex
	 * @param sliceCodeList
	 * @param visceraMapList
	 * @return 
	 */
	TblTissueSliceIndex updateOneFor02(TblTissueSliceIndex tblTissueSliceIndex,
			List<String> sliceCodeList, List<Map<String, Object>> visceraMapList);

	/**查询需镜检但未常规组织取材编号脏器列表（子脏器编号也算编号了），visceraCode,visceraName
	 * @param studyNo
	 * @return
	 */
	List<Map<String, Object>> getRemainVisceraMapListByStudyNo(String studyNo);

	/**根据专题编号 加载列表id,sliceCodeType,createTime,creator,operatorSign
	 * @param studyNo
	 * @return 
	 */
	List<Map<String,Object>> getListByStudyNo(String studyNo);

	/**根据索引id，查询使用组及组名dosageNum,dosageDesc
	 * @param id
	 * @return
	 */
	List<Map<String, Object>> getGroupMapListByIndexId(String id);

	/**加载  固定的脏器或组织，且未被非常规编号的，脏器未被常规编号的
	 * (新建非常规编号，默认加载的已选列表)
	 * Map : animalCode,visceraOrTissueName,visceraType,visceraCode,visceraFixedRecordId
	 * 	visceraName,subVisceraCode,subVisceraName,
	 *	specialFlag,anatomyPosCode,anatomyPos,anatomyFindingCode,
	 *	anatomyFindingFlag,anatomyFingding,bodySurfacePos
	 * @param studyNo
	 */
	List<Map<String,Object>> getFixedVisceraTissueCodeByStudyNo(String studyNo);
	
	/**加载  解剖所见异常的（自溶缺失除外）且未固定未被非常规编号(新建非常规编号，默认加载的待选列表)
	 * Map : animalCode,visceraOrTissueName,visceraType,visceraCode,visceraFixedRecordId
	 * 	visceraName,subVisceraCode,subVisceraName,
	 *	specialFlag,anatomyPosCode,anatomyPos,anatomyFindingCode,
	 *	anatomyFindingFlag,anatomyFingding,bodySurfacePos
	 * @param studyNo
	 */
	List<Map<String,Object>> getAnatomyCheckTissueCodeByStudyNo(String studyNo);

	/**保存切片 索引（切片类型为1），编号，脏器
	 * @param tblTissueSliceIndex（无主键及时间）
	 * @param tblTissueSliceSnList
	 * @param tblTissueSliceVisceraList
	 */
	TblTissueSliceIndex addOneFor1(TblTissueSliceIndex tblTissueSliceIndex,
			List<TblTissueSliceSn> tblTissueSliceSnList,
			List<TblTissueSliceViscera> tblTissueSliceVisceraList);

	/**更新切片 索引（切片类型为1），编号，脏器
	 * @param tblTissueSliceIndex
	 * @param tblTissueSliceSnList
	 * @param tblTissueSliceVisceraList
	 */
	void updateOne(TblTissueSliceIndex tblTissueSliceIndex,
			List<TblTissueSliceSn> tblTissueSliceSnList,
			List<TblTissueSliceViscera> tblTissueSliceVisceraList);

	/** 根据切片编号索引表id，查询切片编号及脏器、组织（异常组织取材编号）
	 * animalCode,visceraOrTissueName,visceraType,visceraCode,visceraFixedRecordId,"+
	 *	visceraName,subVisceraCode,subVisceraName,"+
	 *	specialFlag,anatomyPosCode,anatomyPos,anatomyFindingCode,"+
	 *	anatomyFindingFlag,anatomyFingding,bodySurfacePos,isHandwork
	 * @param indexId
	 * @return
	 */
	List<Map<String, Object>> getSliceCodeByIndexId(String indexId);

	/**根据专题编号，查询 未 切片编号的脏器、组织（异常组织取材编号）
	 * animalCode,visceraOrTissueName,visceraType,visceraCode,visceraFixedRecordId,"+
	 *	visceraName,subVisceraCode,subVisceraName,"+
	 *	specialFlag,anatomyPosCode,anatomyPos,anatomyFindingCode,"+
	 *	anatomyFindingFlag,anatomyFingding,bodySurfacePos
	 * @param studyNo
	 * @return
	 */
	List<Map<String, Object>> getNoSliceCodeListByStudyNo(String studyNo);

	/**查询追加组织取材编号索引表List（sliceTypeCode == 2）
	 * @param studyNo
	 * @return
	 */
	List<TblTissueSliceIndex> getAddToListByStudyNo(String studyNo);

	/**根据切片编号索引表id,查询适用组别范围
	 * @param id
	 * @return
	 */
	List<Integer> getGroupIdListByIndexId(String id);

	/**查询 sliceCode,visceraType,visceraCode,visceraName,subVisceraCode,subVisceraName,sn,subSn    ， 根据索引Id号
	 * @param id
	 * @return
	 */
	List<Map<String, Object>> getSliceSnVisceraMapListByIndexId(String id);

	/**常规或追加 使用过的切片编号个数（已签字）
	 * @param studyNo
	 * @return
	 */
	Integer getSliceCodeNum(String studyNo);

	/**根据专题编号，查询切片编号列表，indexId,sliceCode,visceraOrTissueName
	 * @param studyNo
	 * @return
	 */
	List<Map<String, Object>> getSliceCodeByStudyNo(String studyNo);

	/**加载同一课题下，相近的常规组织取材编号对应的任务Id
	 * @param studyNo
	 * @return
	 */
	String getPreTaskIdByStudyNo(String studyNo);

	/**查询专题对应脏器列表（病理计划或解剖申请合集，剖检或镜检）以外的对应动物种类脏器， 
	 * visceraType,visceraCode,visceraName,subVisceraCode,subVisceraName,sn,subSn
	 * @param studyNo
	 * @param showHistopathViscera   需镜检脏器
	 * @return
	 */
	List<Map<String, Object>> getOtherVisceraCodeAndName(String studyNo,
			boolean showHistopathViscera);

	/**查询该脏器下的子脏器列表， visceraType,visceraCode,visceraName,subVisceraCode,subVisceraName,sn,subSn
	 * @param studyNo
	 * @param visceraCode
	 * @return
	 */
	List<Map<String, Object>> getVisceraMapListByStudyNoVisceraCode(String studyNo, String visceraCode);

	/**查询该脏器列表下的子脏器列表， visceraType,visceraCode,visceraName,subVisceraCode,subVisceraName,sn,subSn
	 * @param studyNo
	 * @param visceraCodeList
	 * @return
	 */
	List<Map<String, Object>> getVisceraMapListByStudyNoVisceraCodeList(String studyNo,
			List<String> visceraCodeList);

	/**根据专题编号，查询动物编号列表
	 * @param studyNo
	 * @return
	 */
	List<Map<String,Object>> getAnimalCodeListByStudyNo(String studyNo);

	/**查询 所有脏器
	 * Map中有 visceraType脏器类型 ，visceraCode 脏器编号，visceraName 脏器名称
	 *  ，subVisceraCode 脏器编号（子），subVisceraName 脏器名称（子）
	 * @param studyNo
	 * @param gender
	 * @return 
	 */
	List<Map<String, Object>> getAllVisceraCodeAndName(String studyNo,int gender);

	/**根据任务id加载列表
	 * @param taskId
	 * @return
	 */
	List<TblTissueSliceIndex> getAllByTaskId(String taskId);

	/**组织取材编号签字确认
	 * @param tblTissueSliceIndex
	 * @param realName
	 * @return
	 */
	TblTissueSliceIndex signTissueSliceIndex(TblTissueSliceIndex tblTissueSliceIndex,
			String realName);

	/**查询非常规组织取材编号索引表List（sliceTypeCode == 1）
	 * @param studyNo
	 * @return
	 */
	List<TblTissueSliceIndex> getFCGListByStudyNo(String studyNo);

	/**获取非常规编号  动物编号及对应的编号数量（签字确认过的编号）
	 * @param studyNo
	 * @return   map:动物编号->对应数量
	 */
	Map<String, Integer> getAnimalCode2NumberMap(String studyNo);
}
