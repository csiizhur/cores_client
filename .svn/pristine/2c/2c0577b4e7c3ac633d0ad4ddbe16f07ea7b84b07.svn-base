package com.lanen.service.path;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lanen.base.BaseDao;
import com.lanen.jsonAndModel.Json;
import com.lanen.model.path.TblAnatomyAnimal;

/**解剖动物信息service
 * @author 
 *
 */
/**
 * @author 黄国刚
 *
 */
public interface TblAnatomyAnimalService extends BaseDao<TblAnatomyAnimal> {
	
	/**
	 * 根据会话id列表，返回对应动物列表（mapList，map中对应字段有：taskId,studyNo，
	 * animalCode，sessionCreator操作者 ，anatomyCheckFinishFlag剖检完成标识,autolyzeFlag自溶标识,
	 * visceraWeighFinishFlag称重完成标识,visceraFixedFinishFlag固定完成标识,
	 * visceraFixedWeighFinishFlag固定后称重完成标识,anatomyOperator解剖者,
	 * anatomyBeginTime解剖开始时间
	 * ）
	 * @param sessionIdList
	 * @return
	 */
	List<Map<String, Object>> getListBySessionIdList(List<String> sessionIdList);
	/**根据会话ID列表查询动物编号（如果有不同的课题下动物编号重复需去重）
	 * @param sessionIdList
	 * @param sessionId
	 * @return
	 */
	List<Map<String, Object>> getAnimalCodeBySessionIdList(List<String> sessionIdList,String studyNo);
//	/**
//	 * 更新会话id列表，返回对应动物列表（mapList，map中对应字段有：taskId,studyNo，
//	 * animalCode，realName操作者 ，visceraWeightFinishSign称重完成签字
//	 * ，visceraFixedFinishSign固定完成签字，anatomyCheckFinishFlag剖检完成标识,autolyzeFlag自溶标识）
//	 * @param sessionIdList
//	 * @return
//	 */
//	List<Map<String, Object>> getListBySessionIdList2(List<String> sessionIdList);

	/**根据任务id列表会话类型，查询待解剖或称重或固定的动物列表(mapList);
	 * Map中有studyNo,anatomyNum,animalCode,gender,animalId ,taskId,anatomyOperator
	 * @param taskIdList
	 * @param sessionType
	 * @param showAnatomyNoFinishAnimal 显示已解剖且尚未完成的动物（sessionType == 2， 6）
	 * 	 * @return
	 */
	List<Map<String,Object>> getMapListByTaskIdListSessionType(List<String> taskIdList,Integer sessionType, boolean showAnatomyNoFinishAnimal);
	
	/**保存或更新解剖动物信息,返回map（map中对应字段有：taskId,studyNo，
	 * animalCode，realName操作者 ，visceraWeightFinishSign称重完成签字
	 * ，visceraFixedFinishSign固定完成签字，anatomyCheckFinishFlag剖检完成标识,autolyzeFlag自溶标识,出错时msg存放错误信息，否则为""）
	 * @param taskId
	 * @param sessionId
	 * @param sessionType
	 * @param animalCode
	 * @param date      解剖开始时间
	 * @param userName  解剖者
	 * @param deadDate  死亡日期
	 * @return
	 */
	Map<String, Object> saveOrUpdate(String taskId, String sessionId, Integer sessionType, String animalCode, String userName, Date anatomyBeginTime, Date deadDate);
	 /**
	  * 
	  * @param studyNo
	  * @param animalCode
	  * @param hostName
	  * @param realName
	  * @param reason
	  * @param deadDate
	  * @return
	  */
	Map<String, Object> updateAnatomyAnimalDeadDate(String studyNo,String animalCode,String hostName,String realName,String reason, Date deadDate) ;
	
	/**根据 任务id及动物编号 查询实体
	 * @param taskId
	 * @param animalCode
	 * @return
	 */
	TblAnatomyAnimal getByTaskIdAnimalCode(String taskId,
			String animalCode) ;
	/**根据 任务id及动物编号,判断实体是否存在
	 * @param taskId
	 * @param animalCode
	 * @return
	 */
	boolean isExistByTaskIdAnimalCode(String taskId, String animalCode);

	/**判断动物剖检是否完成
	 * @param studyNo
	 * @param animalCode
	 * @return
	 */
	boolean isAnatomyFinish(String studyNo, String animalCode);
	
	/**判断动物脏器固定是否完成
	 * @param studyNo
	 * @param animalCode
	 * @return
	 */
	boolean isVisceraFixedFinish(String studyNo, String animalCode);
	
	/**根据 任务id及动物编号 查询实体
	 * @param taskId
	 * @param animalCode
	 * @return
	 */
	TblAnatomyAnimal getByStudyNoAnimalCode(String studyNo,
			String animalCode) ;
	
	/** 标记动物解剖完成
	 * @param animalCode 
	 * @param studyNo 
	 * @return
	 */
	Json setAnatomyFinish(String studyNo, String animalCode);
	
	/**设置动物 自溶标识（成功  true，失败  false：msg）
	 * @param taskId
	 * @param animalCode
	 * @return
	 */
	Json setAutolyzeFlag(String taskId, String animalCode);
	
	/**设置 脏器固定完成签字（暂且没签字，设为 “1”）
	 * @param studyNo
	 * @param animalCode
	 * @return
	 */
	Json setVisceraFixedFinishSign(String studyNo, String animalCode);
	
	/**设置 脏器称重完成签字（暂且没签字，设为 “1”）
	 * @param studyNo
	 * @param animalCode
	 * @return
	 */
	Json setVisceraWeightFinishSign(String studyNo, String animalCode,Boolean isFixed);
	/**根据会话类型和会话ID查询会话下还未完成会话任务的动物
	 * @param sessionType
	 * @param sessionId
	 * @return
	 */
	List<TblAnatomyAnimal> getListBySessionTypeAndSessionId(
			int sessionType, String sessionId);
	
	/**
	 * 根据会话Id查询脏器缺失的动物（自溶的动物和脏器自溶的动物）
	 * @param sessionIdList
	 * @return
	 */
	int getSumAutolyzeFlagAnimalCount(List<String> sessionIdList);
	
	
	/**
	 * 根基sessionid 获得动物固定完成时间和动物编号
	 * @param sessionId
	 * @return
	 */
	List<Map<String, Object>> getListBySessionId(String sessionId);
	/**根据动物状态和会话ID查找任务下已解剖的动物
	 * @param taskId
	 * @param animalState
	 * @param sessionId
	 * @return
	 */
	List<Map<String, Object>> getListByStateAndSessionId(String taskId,
			String animalState, String sessionId);
	/**根据动物状态和会话ID查找任务下已解剖的动物
	 * @param taskId
	 * @param animalState
	 * @param sessionId
	 * @return
	 */
	List<Map<String, Object>> getListByStateAndSessionIds(String taskId,
			String animalState, String sessionId);
	
	/**获取当前时间
	 * @return
	 */
	Date getCurrentDate();
	
	
	/**查询已称重完成，未固定的动物列表
	 * @param sessionIdList
	 * @return
	 */
	List<String> getNoFixedFinishAnimalList(List<String> sessionIdList);
	
	/**删除动物（解释时选错的，未有解剖记录等）
	 * @param studyNo
	 * @param animalCode
	 * @return
	 */
	Json deleteAimal(String studyNo, String animalCode,String operator);
	
	/**根据任务Id查询动物列表，animalCode,gender,autolyzeFlag
	 * @param taskId
	 * @return
	 */
	List<Map<String, Object>> getMapListByTaskId(String taskId);
	
	/**
	 * 根据会话id列表，返回对应动物列表（mapList，map中对应字段有：taskId,studyNo，
	 * animalCode，sessionCreator操作者 ，anatomyCheckFinishFlag剖检完成标识,autolyzeFlag自溶标识,
	 * visceraWeighFinishFlag称重完成标识,visceraFixedFinishFlag固定完成标识,
	 * visceraFixedWeighFinishFlag固定后称重完成标识,anatomyOperator解剖者,
	 * anatomyBeginTime解剖开始时间
	 * ）
	 * @param sessionIdList
	 * 以解剖开始时间排序
	 * @return
	 */
	List<Map<String, Object>> getListBySessionIdListOrderByAnatomyBeginTime(
			List<String> sessionIdList2);
	
	/**查询固定超时（25分钟）动物列表，且尚未固定完成
	 * @param sessionIdList
	 * @return
	 */
	List<String> fixedTimeoutBySessionIdList(List<String> sessionIdList);
}
