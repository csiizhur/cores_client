package com.lanen.service.path;

import java.util.List;
import java.util.Map;

import com.lanen.base.BaseDao;
import com.lanen.model.path.TblPathSession;

/**病理检查会话
 * @author 黄国刚
 *
 */
public interface TblPathSessionService extends BaseDao<TblPathSession>{

	/**保存会话s，并返回sessionIdList
	 * @param sessionType 会话类型
	 * @param userName    创建者（用户名）
	 * @param taskIdList  任务id列表
	 * @return
	 */
	List<String> saveList(int sessionType, String userName, List<String> taskIdList);

	/**查询List<Map> ,map 中有 sessionId,  taskId,  studyNo,
	 *  sessionType,  sessionCreator, createTime,  finishSigner,
	 * finishSignDate;已sessionId 降序排列
	 * @param taskIdList
	 * @param sessionType
	 * @return
	 */
	List<Map<String, Object>> getMapList(List<String> taskIdList, int sessionType);

	/**在sessionIdList中，找出一个与任务id对应的sessionId
	 * @param taskId
	 * @param sessionIdList
	 * @return
	 */
	String getSessionIdByTaskIdSessionIdList(String taskId, List<String> sessionIdList);

	/** 才方法仅限 解剖页面上使用
	 * @param sessionIdList
	 * @return
	 */
	Map<String, String> getTaskIdSessionIdMapBySessionIdList(List<String> sessionIdList);

	/**根据会话ID批量更新会话完成签字
	 * @param userName
	 * @param sessionIdList
	 */
	void updateListBySessionIdList(String userName, List<String> sessionIdList);

	/**保存会话及操作者列表，并返回会话idList
	 * @param sessionType
	 * @param userName
	 * @param taskIdList
	 * @param userNameList
	 * @return
	 */
	List<String> saveList(int sessionType, String userName, List<String> taskIdList,
			List<String> userNameList);

	/**查询 会话对应的操作者    用户名、真实姓名    map 中有      userName 、realName
	 * @param sessionIdList
	 * @return
	 */
	List<Map<String, Object>> getUserNameRealName(List<String> sessionIdList);

	/**删除原解剖者，添加新解剖者
	 * @param string
	 * @param userNameList
	 */
	void updateOperatorList(String string, List<String> userNameList);
	
	/**
	 * 根据专题编号获取所有的病理专题负责人
	 * @param studyNoList
	 * @return
	 */
	List<Map<String, Object>> getAllPathSD(List<String> studyNoList);

	/** 获得对应专题负责人
	 * @param studyNo
	 * @return
	 */
	String getPathSD(String studyNo);

	/**根据任务ID查找所有有剖检的会话
	 * @param taskId
	 * @param tabIndex 
	 * @return
	 */
	List<Map<String, Object>> getAnatomySessionListByTaskId(String taskId, int tabIndex);
	/**根据一个或多个任务ID查找所有有剖检的会话
	 * @param taskId
	 * @param tabIndex 
	 * @return
	 */
	List<Map<String, Object>> getAnatomySessionListByTaskIds(String taskId, int tabIndex);

}