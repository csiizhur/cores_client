package com.lanen.service.path;

import java.util.List;
import java.util.Map;

import com.lanen.base.BaseDao;
import com.lanen.jsonAndModel.Json;
import com.lanen.model.path.TblVisceraWeight;

/**脏器称重Service
 * @author 黄国刚
 *
 */
public interface TblVisceraWeightService extends BaseDao<TblVisceraWeight> {
		
	/**查询待称重脏器列表，
	 * 1:固定前称重
	 * 	1.1 解剖申请-脏器称重（包括附件脏器），
	 * 	1.2剖检记录里的脏器
	 * 2:固定后称重
	 * 	2.1剖检记录里的脏器
	 *  
	 *  返回值mapList里的Map 含有 visceraType脏器类型,visceraCode 脏器编号,visceraName 脏器名称
	 *  ,subVisceraCode 脏器编号（子）,subVisceraName 脏器名称（子）,partVisceraSeparateWeigh 成对脏器分开称重,
	 *  attachedVisceraFlag 是否有附件脏器,attachedVisceraNames 附加脏器名称
	 * @param taskId
	 * @param animalCode
	 * @param isFixed     true:固定后称重   false:固定前称重
	 * @return
	 */
	List<Map<String, Object>> getVisceraMapListByTaskIdAnimalCode(String taskId, String animalCode,boolean isFixed);
	
	/**
	 * 根据会话Id查询脏器称重信息
	 * @param sessionID
	 * @param visceraNameSelected 
	 * @param animalCodeSelected 
	 * @param studyNoSelected 
	 * @return
	 */
	List<Map<String, Object>> getVisceraMapListBySessionID(List<String> sessionID, String studyNoSelected, String animalCodeSelected, String visceraNameSelected);
	
	

	/**保存一个称重记录，无id
	 * @param tblVisceraWeight
	 */
	void saveOne(TblVisceraWeight tblVisceraWeight);

	/**保存或更新称重记录（studyNo，animalCode，visceraCode ,subVisceraCode是否存在）
	 * @param tblVisceraWeight
	 * @return 
	 */
	String saveOrUpdateOne(TblVisceraWeight tblVisceraWeight);

	/**查询一实体
	 * @param studyNo
	 * @param animalCode
	 * @param visceraCode
	 * @param subVisceraCode
	 * @return
	 */
	TblVisceraWeight getOne(String studyNo,String animalCode,String visceraCode,String subVisceraCode);

	/**查询实体列表
	 * @param studyNo
	 * @param animalCode
	 * @return
	 */
	List<TblVisceraWeight> getList(String studyNo, String animalCode);

	/**查询同组同性别其他动物 该脏器重量，返回值mapList（map:animalCode,weight,weightUnit）
	 * @param studyNo
	 * @param animalCode
	 * @param visceraCode
	 * @param subVisceraCode
	 * @return
	 */
	List<Map<String, Object>> getOtherAnimalWeightMapList(String studyNo, String animalCode,
			String visceraCode, String subVisceraCode);
	
	/**
	 * 根据sessionID，taskIdList查看已称重的动物
	 * @return
	 */
	int countAnimalWeightBySessionID(List<String> sessionID);
	
	
	/**
	 * 根据会话Id获得打印数据
	 * @param sessionid
	 * @return
	 */
	List<Map<String,Object>> getPrintVisceraWeightServiceBySession(String sessionid);

	/**根据任务ID和会话ID查询已经称重的脏器列表
	 * @param taskId
	 * @param sessionId
	 * @return
	 */
	List<Map<String, Object>> getVisceraNameAndCodeList(String taskId,
			String sessionId);
	/**根据任务ID和会话ID查询已经称重的脏器列表
	 * @param taskId
	 * @param sessionId
	 * @return
	 */
	List<Map<String, Object>> getVisceraNameAndCodeLists(String taskId,
			String sessionId);

	/**根据任务ID和会话ID查询已经称重的动物列表
	 * @param taskId
	 * @param sessionId
	 * @return
	 */
	List<Map<String, Object>> getAnimalCodeBySessionId(String taskId,
			String sessionId);
	/**根据任务ID和会话ID查询已经称重的动物列表
	 * @param taskId
	 * @param sessionId
	 * @return
	 */
	List<Map<String, Object>> getAnimalCodeBySessionIds(String taskId,
			String sessionId);

	/**根据条件查找称重记录
	 * @param taskId
	 * @param sessionId
	 * @param visceraName
	 * @param animalCode
	 * @return
	 */
	List<Map<String, Object>> getListByConditions(String taskId,
			String sessionId, String visceraName, String animalCode);
	/**根据条件查找称重记录
	 * @param taskId
	 * @param sessionId
	 * @param visceraName
	 * @param animalCode
	 * @return
	 */
	List<Map<String, Object>> getListByConditionss(String taskId,
			String sessionId, String visceraName, String animalCode);

	/** 删除称重记录保存称重历史记录，删除成功  返回 true， 否则返回false及msg
	 * @param id
	 * @param reason
	 * @param operator
	 * @return
	 */
	Json deleteOne(String id, String reason, String operator);

	/**获得称重列表
	 * @param studyNo
	 * @param animalCode
	 * @param fixedFlag   是否固定后称重
	 * @return
	 */
	List<TblVisceraWeight> getList(String studyNo, String animalCode, boolean fixedFlag);

	/**查询病理计划里，待称重脏器列表，补录脏器称重用 getVisceraMapListByTaskIdAnimalCode
	 * @param taskId
	 * @param studyNo
	 * @return
	 */
	List<Map<String, Object>> getVisceraMapListByTaskIdStudyNo(String taskId, String studyNo);

	/**查询该任务下所有称重数据
	 * @param taskId
	 * @return
	 */
	List<TblVisceraWeight> getListByTaskId(String taskId);
	

}
