package com.lanen.service.path;

import java.util.List;
import java.util.Map;

import com.lanen.base.BaseDao;
import com.lanen.jsonAndModel.Json;
import com.lanen.model.path.TblAnatomyCheck;

/**
 * 剖检记录service
 * @author Administrator
 *
 */
public interface TblAnatomyCheckService extends BaseDao<TblAnatomyCheck> {
	

	/**查询剖检的脏器列表列表(mapList)(涉及到动物、性别)
	 *  Map中有 visceraType脏器类型 ，visceraCode 脏器编号，visceraName 脏器名称
	 *  ，subVisceraCode 脏器编号（子），subVisceraName 脏器名称（子）
	 * @param taskId
	 * @param animalCode
	 * @param studyNo
	 * @return
	 */
	List<Map<String, Object>> getVisceraCodeAndName(String taskId,String studyNo,String animalCode);
	
	/**查询剖检脏器(其他)列表(mapList)(涉及到动物、性别)
	 *  Map中有 visceraType脏器类型 ，visceraCode 脏器编号，visceraName 脏器名称
	 *  ，subVisceraCode 脏器编号（子），subVisceraName 脏器名称（子）
	 * @param taskId
	 * @param animalCode
	 * @param studyNo
	 * @return
	 */
	List<Map<String, Object>> getOtherVisceraCodeAndName(String taskId,String studyNo,String animalCode);
	
	/**根据studyNo，查询剖检的脏器列表(mapList);
	 *  Map中有 visceraType脏器类型 ，visceraCode 脏器编号，visceraName 脏器名称
	 *  ，subVisceraCode 脏器编号（子），subVisceraName 脏器名称（子）
	 * @param taskid
	 * @return
	 */
	List<Map<String, Object>> getVisceraCodeAndNameByStudyNo(String studyNo);

	/**查询   脏器字典列表
	 * 返回mapList（Map中有 visceraType脏器类型 ，visceraCode 脏器编号，visceraName 脏器名称
	 *  ，subVisceraCode 脏器编号（子），subVisceraName 脏器名称（子），序号）
	 * @param taskId
	 * @return
	 */
	List<Map<String,Object>> getVisceraMapList();

	/**根据会话Id及动物编号，查询列表
	 * @param sessionId
	 * @param animalCode 
	 * @return
	 */
	List<TblAnatomyCheck> getListBySessionId(String sessionId, String animalCode);

	/**保存实体，返回json(成功与否)
	 * @param tblAnatomyCheck
	 * @return
	 */
	Json saveOne(TblAnatomyCheck tblAnatomyCheck);
	
	/**更新实体，返回json(成功与否)
	 * @param tblAnatomyCheck
	 * @return
	 */
	Json updateOne(TblAnatomyCheck tblAnatomyCheck);

	/**删除，成功   true，失败false：msg
	 * @param id
	 * @return
	 */
	Json deleteOne(String id);
	
	/**
	 * 根据会话ID查询异常动物数量
	 * @param sessionID
	 * @return
	 */
	int getAnatomyCheckSumBySessionID(List<String> sessionID);
	

	
	/**
	 * 根据会话ID,专题编号，动物编号，脏器编号查询解剖所见
	 * @param sessionIDList
	 * @param visceraNameSelected 
	 * @param animalCodeSelected 
	 * @param studyNoSelected 
	 * @return
	 */
	List<Map<String,Object>> getAnatomyCheckBySessionIDs(List<String> sessionIDList, String studyNoSelected, String animalCodeSelected, String visceraNameSelected);
	
	
	/**
	 * 根据会话ID，任务id查询脏器
	 * @param sessionID
	 * @return
	 */
	List<Map<String,Object>> getVisceraNameBySessionIDsAndTaskIdList(List<String> sessionID,List<String> taskIdList);

	/**根据课题编号和动物编号查询剖检记录(仅肿物)
	 * @param studyNo
	 * @param animalCode
	 * @return
	 */
	List<Map<String,Object>> getListByStudyNoAndAnimalCode(String studyNo,
			String animalCode);
	
	/**根据课题编号和动物编号查询剖检记录(仅肿物 or 全部)
	 * @param studyNo
	 * @param animalCode
	 * @param isShowOther   true：全部    false：仅肿物
	 * @return
	 */
	List<Map<String,Object>> getListByStudyNoAndAnimalCode(String studyNo,
			String animalCode,boolean isShowOther);

	/**判断该脏器是否已标记自溶
	 * @param sessionId
	 * @param animalCode
	 * @param visceraCode
	 * @param subVisceraCode   子脏器编号未空  ，则为一级脏器，不为空为二级脏器
	 * @return
	 */
	boolean isHasAutolyze(String sessionId, String animalCode, String visceraCode,
			String subVisceraCode);
	
	/**判断该脏器是否有记录（包括自溶）
	 * @param sessionId
	 * @param animalCode
	 * @param visceraCode
	 * @param subVisceraCode   子脏器编号未空  ，则为一级脏器，不为空为二级脏器
	 * @return
	 */
	boolean isHasRecord(String sessionId, String animalCode, String visceraCode,
			String subVisceraCode);
	
	/**
	 * 更新使用频度，调用存储过程updateFreqCount
	 */
	void updateFreqCount();

	/**得到按计划还未固定的脏器名称及是否自溶标志
	 * @param sessionId
	 * @param studyNo
	 * @param animalCode
	 * @param notFixedVisceraList
	 * @return
	 */
	List<Map<String, Object>> getVisceraNameAndisHasAutolyzeMap(
			String sessionId, String studyNo, String animalCode,
			List<String> notFixedVisceraList);

	/**根据脏器名称等条件查询实体
	 * @param sessionId
	 * @param studyNo
	 * @param animalCode
	 * @param visceraName
	 * @return
	 */
	TblAnatomyCheck getByVisceraName(String sessionId, String studyNo,
			String animalCode, String visceraName);
	
	
	/**
	 * 根据专题编号和动物号查询症状观察
	 * @param studyNo
	 * @param animalCode
	 * @return
	 */
	List<Map<String, Object>> getTbWatchListByStudyNoAndAnimalCode( String studyNo, String animalCode);
	
	
	/**
	 * 根据sessionidList查询剖检打印记录表
	 * @param sessionid
	 * @return
	 */
	List<Map<String,Object>> getTblAnatomyCheckPrint(List<String> sessionidList);
	
	/**
	 * 根据sessionidList查询剖检打印记录表(子脏器一个有异常，其他显示未见异常)
	 * @param sessionid
	 * @return
	 */
	List<Map<String,Object>> getTblAnatomyCheckPrint_new(List<String> sessionidList);

	/**根据会话Id和任务ID查找解剖所见脏器名称和编号
	 * @param taskId
	 * @param sessionId
	 * @return
	 */
	List<Map<String, Object>> getVisceraNameAndCodeList(String taskId,
			String sessionId);
	/**根据会话Id和任务ID查找解剖所见脏器名称和编号
	 * @param taskId
	 * @param sessionId
	 * @return
	 */
	List<Map<String, Object>> getVisceraNameAndCodeLists(String taskId,
			String sessionId);
	/**根据会话Id和任务ID查找解剖所见脏器名称和编号
	 * @param taskId
	 * @param sessionId
	 * @return
	 */
	List<Map<String, Object>> getVisceraNameAndCodeListWithEdits(String taskId,
			String sessionId);
	
	/**根据会话ID和任务ID查询解剖所见
	 * @param taskId
	 * @param sessionId
	 * @return
	 */
	List<Map<String, Object>> getAnatomyFindingBySessionId(String taskId,
			String sessionId);
	/**根据会话ID和任务ID查询解剖所见
	 * @param taskId
	 * @param sessionId
	 * @return
	 */
	List<Map<String, Object>> getAnatomyFindingBySessionIds(String taskId,
			String sessionId);
	/**根据会话ID和任务ID查询解剖所见
	 * @param taskId
	 * @param sessionId
	 * @return
	 */
	List<Map<String, Object>> getAnatomyFindingBySessionIdWithEdits(String taskId,
			String sessionId);

	/**根据会话ID和任务ID查询动物列表
	 * @param taskId
	 * @param sessionId
	 * @return
	 */
	List<Map<String, Object>> getAnimalCodeBySessionId(String taskId,
			String sessionId);
	/**根据会话ID和任务ID查询动物列表
	 * @param taskId
	 * @param sessionId
	 * @return
	 */
	List<Map<String, Object>> getAnimalCodeBySessionIds(String taskId,
			String sessionId);

	/**根据会话ID和任务ID，脏器名称，解剖所见，动物编号
	 * @param taskId
	 * @param sessionId
	 * @param visceraName
	 * @param antomyFinding
	 * @param animalCode
	 * @return
	 */
	List<Map<String, Object>> getListByConditions(String taskId,
			String sessionId, String visceraName, String antomyFinding,
			String animalCode);
	/**根据会话ID和任务ID，脏器名称，解剖所见，动物编号
	 * @param taskId
	 * @param sessionId
	 * @param visceraName
	 * @param antomyFinding
	 * @param animalCode
	 * @return
	 */
	List<Map<String, Object>> getListByConditions2(String taskId,
			String sessionId, String visceraName, String antomyFinding,
			String animalCode);
	/**根据会话ID和任务ID，脏器名称，解剖所见，动物编号
	 * @param taskId
	 * @param sessionId
	 * @param visceraName
	 * @param antomyFinding
	 * @param animalCode
	 * @return
	 */
	List<Map<String, Object>> getListByConditions2s(String taskId,
			String sessionId, String visceraName, String antomyFinding,
			String animalCode);
	/**根据会话ID和任务ID，脏器名称，解剖所见，动物编号
	 * @param taskId
	 * @param sessionId
	 * @param visceraName
	 * @param antomyFinding
	 * @param animalCode
	 * @return
	 */
	List<Map<String, Object>> getListByConditions2WithEdits(String taskId,
			String sessionId, String visceraName, String antomyFinding,
			String animalCode);

	/**判断该脏器是否已标记缺失
	 * @param sessionId
	 * @param animalCode
	 * @param visceraCode
	 * @param subVisceraCode   子脏器编号未空  ，则为一级脏器，不为空为二级脏器
	 * @return
	 */
	boolean isMissing(String sessionId, String animalCode, String visceraCode, String subVisceraCode);

	/**保存实体，返回json(成功与否)
	 * @param tblAnatomyCheck
	 * @param missingRsn
	 */
	Json saveOne(TblAnatomyCheck tblAnatomyCheck, String missingRsn);

	/**缺如原因
	 * @param anatomyCheckId
	 * @return
	 */
	String getMissRsnByAnatomyCheckId(String anatomyCheckId);

	/**保存实体，返回json(成功与否),数据确认时使用
	 * @param tblAnatomyCheck
	 * @param missingRsn
	 * @return
	 */
	Json saveOne_1(TblAnatomyCheck tblAnatomyCheck, String missingRsn);

	/**保存实体，返回json(成功与否),数据确认时使用
	 * @param tblAnatomyCheck
	 * @return
	 */
	Json saveOne_1(TblAnatomyCheck tblAnatomyCheck);

	/**保存实体同时保存固定记录，返回json(成功与否),数据确认时使用
	 * @param tblAnatomyCheck
	 * @return
	 */
	Json saveOne_2(TblAnatomyCheck tblAnatomyCheck);
	
	/**更新实体，删除缺如原因
	 * @param tblAnatomyCheck
	 * @return
	 */
	Json updateOne_1(TblAnatomyCheck tblAnatomyCheck);

	/**更新实体，删除缺如原因，添加新原因
	 * @param tblAnatomyCheck
	 * @param missingRsn
	 * @return
	 */
	Json updateOne_1(TblAnatomyCheck tblAnatomyCheck, String missingRsn);

	/**删除实体及对应缺失原因
	 * @param id
	 */
	void deleteOne_1(String id);

	/**根据课题编号及动物编号，查询列表
	 * @param studyNo
	 * @param animalCode
	 * @return
	 */
	List<TblAnatomyCheck> getListByStudyNoAnimalCode(String studyNo, String animalCode);

	/**查询需剖检的脏器列表列表(mapList)(涉及到动物、性别),补录解剖随见专用，查询病理计划（非解剖申请）
	 *  Map中有 visceraType脏器类型 ，visceraCode 脏器编号，visceraName 脏器名称
	 *  ，subVisceraCode 脏器编号（子），subVisceraName 脏器名称（子）
	 * @param taskId
	 * @param animalCode
	 * @param studyNo
	 * @return
	 */
	List<Map<String, Object>> getVisceraCodeAndName_additional(String taskId, String studyNo,
			String animalCode);

	/**判断该脏器是否有记录（包括自溶）
	 * @param studyNo
	 * @param animalCode
	 * @param visceraCode
	 * @param subVisceraCode子脏器编号未空  ，则为一级脏器，不为空为二级脏器
	 * @return
	 */
	boolean isHasRecord_1(String studyNo, String animalCode, String visceraCode,
			String subVisceraCode);

	/**判断该脏器是否已标记自溶
	 * @param studyNo
	 * @param animalCode
	 * @param visceraCode
	 * @param subVisceraCode子脏器编号未空  ，则为一级脏器，不为空为二级脏器
	 * @return
	 */
	boolean isHasAutolyze_1(String studyNo, String animalCode, String visceraCode,
			String subVisceraCode);

	/**判断该脏器是否已标记缺失
	 * @param studyNo
	 * @param animalCode
	 * @param visceraCode
	 * @param subVisceraCode子脏器编号未空  ，则为一级脏器，不为空为二级脏器
	 * @return
	 */
	boolean isMissing_1(String studyNo, String animalCode, String visceraCode, String subVisceraCode);

	/**查询该任务下的所有解剖
	 * @param taskId
	 * @return
	 */
	List<TblAnatomyCheck> getListByTaskId(String taskId);

	/**保存实体同时保存固定记录及固定历史记录，返回json(成功与否),数据确认时使用
	 * @param tblAnatomyCheck
	 * @param operateRsn
	 * @return
	 */
	Json saveOne_3(TblAnatomyCheck tblAnatomyCheck, String operateRsn);
}
