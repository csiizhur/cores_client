package com.lanen.service.path;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lanen.base.BaseDao;
import com.lanen.jsonAndModel.Json;
import com.lanen.model.path.TblVisceraFixed;

/**脏器固定记录Service
 * @author 黄国刚
 *
 */
public interface TblVisceraFixedService extends BaseDao<TblVisceraFixed>{

	/** 查询脏器（一级）列表（解剖申请-脏器/组织学检查 表中的 脏器（对应申请的）和剖检记录表中的脏器（对应动物的））（Map里存放的是 visceraType,visceraName,visceraCode,sn）；
	 * @param taskId
	 * @param sessionId
	 * @param animalCode
	 * @return
	 */
	List<Map<String,Object>> getVisceraMapListByTaskIdSessionIdAnimalCode(String taskId,String sessionId,String animalCode);

	/**删除数据，成功 返回   success：true ，失败 success：false及msg：“”
	 * @param id
	 * @return
	 */
	Json deleteOne(String id);

	/**查询列表，根据 sessionId，animalCode
	 * @param sessionId
	 * @param animalCode
	 * @return
	 */
	List<TblVisceraFixed> getListBySessionIdAnimalCode(String sessionId, String animalCode);

	/**保存固定信息列表  ，成功 返回   success：true ，失败 success：false及msg：“”
	 * @param sessionId
	 * @param studyNo
	 * @param animalCode
	 * @param userName
	 * @param date
	 * @param selectedMapList   (map:visceraType,visceraName,visceraCode,sn)
	 * @return
	 */
	Json saveList(String sessionId, String studyNo, String animalCode, String userName, Date date,
			List<Map<String, Object>> selectedMapList);
	/**查询列表，根据sessionIds(会话ID列表)，animalCode(动物编号)，visceraName(脏器名称)
	 * @param sessionIds
	 * @param studyNoSelected
	 * @param animalCodeSelected
	 * @param visceraNameSelected 
	 * @return
	 */
	List<Map<String,Object>> getListBySessionIdAnimalCodeVisceraName(List<String> sessionIds, String studyNoSelected,String animalCodeSelected, String visceraNameSelected);
	/**查询列表，根据sessionIds(会话ID列表)，animalCode(动物编号)，visceraName(脏器名称)
	 * @param sessionIds
	 * @param studyNoSelected
	 * @param animalCodeSelected
	 * @param visceraNameSelected 
	 * @return
	 */
	List<Map<String,Object>> getListBySessionIdAnimalCodeVisceraName2(List<String> sessionIds, String studyNoSelected,String animalCodeSelected, String visceraNameSelected);
	/**根据sessionIds(会话ID列表)查询已经固定的动物数量
	 * @param sessionIds
	 * @return
	 */
	Integer getAnimalCountBySessionIds(List<String> sessionIds);

	/**保存异常脏器固定
	 * @param abnAnotomyCheckIdList
	 * @param listFixed
	 */
	void saveAbnFixedList(List<String> abnAnotomyCheckIdList,
			List<TblVisceraFixed> listFixed);

	/**根据剖检记录ID查询实体
	 * @param anatomyCheckRecordId
	 * @return
	 */
	TblVisceraFixed getByAnatomyCheckId(String anatomyCheckRecordId);

	/**根据课题编号和动物编号查询固定的非正常病变
	 * @param studyNo
	 * @param animalCode
	 * @return
	 */
	List<TblVisceraFixed> getListByStudyNoAnimalCode(String studyNo,
			String animalCode);
	
	/**
	 * 根据会话id查询非常规组织病变
	 * @param sessiond
	 * @return
	 */
	List<Map<String,Object>> getListBySessionid(String sessionid);
	/**
	 * 根据会话id查询非常规组织病变
	 * @param sessiond
	 * @return
	 */
	List<Map<String,Object>> getNormallessListByTaskIdAndSessionid(String taskId,String sessionid);
	/**
	 * 根据会话id查询非常规组织病变
	 * @param sessiond
	 * @return
	 */
	List<Map<String,Object>> getNormallessListByTaskIdAndSessionids(String taskId,String sessionid);
	
	/**根据课题编号，动物编号，脏器名称查找实体
	 * @param studyNo
	 * @param animalCode
	 * @param visceraName
	 * @return
	 */
	TblVisceraFixed getByVisceraName(String studyNo, String animalCode,
			String visceraName);
	
	
	/**得到无需称重的所有需要固定的脏器信息
	 * @param taskId
	 * @param sessionId
	 * @param animalCode
	 * @param studyNo
	 * @return
	 */
	List<Map<String, Object>> getNeedNotWeighMapList(String taskId,
			String sessionId, String animalCode, String studyNo);

	/**根据任务ID和会话ID查找脏器列表
	 * @param taskId
	 * @param sessionId
	 * @return
	 */
	List<Map<String, Object>> getVisceraNameAndCodeList(String taskId,
			String sessionId);
	/**根据任务ID和会话ID查找脏器列表
	 * @param taskId
	 * @param sessionId
	 * @return
	 */
	List<Map<String, Object>> getVisceraNameAndCodeLists(String taskId,
			String sessionId);

	/**根据任务ID和会话ID查找动物列表
	 * @param taskId
	 * @param sessionId
	 * @return
	 */
	List<Map<String, Object>> getAnimalCodeBySessionId(String taskId,
			String sessionId);
	/**根据任务ID和会话ID查找动物列表
	 * @param taskId
	 * @param sessionId
	 * @return
	 */
	List<Map<String, Object>> getAnimalCodeBySessionIds(String taskId,
			String sessionId);

	/**根据条件查找任务下脏器固定记录
	 * @param taskId
	 * @param sessionId
	 * @param visceraName
	 * @param animalCode
	 * @return
	 */
	List<Map<String, Object>> getListByConditions(String taskId,
			String sessionId, String visceraName, String animalCode);
	/**根据条件查找任务下脏器固定记录
	 * @param taskId
	 * @param sessionId
	 * @param visceraName
	 * @param animalCode
	 * @return
	 */
	List<Map<String, Object>> getListByConditionss(String taskId,
			String sessionId, String visceraName, String animalCode);
	/**根据条件查找任务下脏器固定交叉表记录
	 * @param taskId
	 * @param sessionId
	 * @param visceraName
	 * @param animalCode
	 * @return
	 */
	List<Map<String, Object>> getListByConditions2(String taskId,
			String sessionId, String visceraName, String animalCode);
	/**根据条件查找任务下脏器固定交叉表记录
	 * @param taskId
	 * @param sessionId
	 * @param visceraName
	 * @param animalCode
	 * @return
	 */
	List<Map<String, Object>> getListByConditions2s(String taskId,
			String sessionId, String visceraName, String animalCode);

	/**该脏器是否固定
	 * @param studyNo
	 * @param animalCode
	 * @param visceraCode
	 * @return
	 */
	boolean isFixed(String studyNo, String animalCode, String visceraCode);

	/**除subVisceraCode外，该主脏器下子脏器是否都已经缺失
	 * @param studyNo
	 * @param animalCode
	 * @param visceraCode
	 * @param subVisceraCode
	 * @return
	 */
	boolean isAllMissing(String studyNo, String animalCode, String visceraCode,
			String subVisceraCode);
}
