package com.lanen.service.path;

import java.util.List;
import java.util.Map;

import com.lanen.base.BaseDao;
import com.lanen.jsonAndModel.Json;
import com.lanen.model.User;
import com.lanen.model.path.TblAnatomyAnimal;
import com.lanen.model.path.TblAnatomyTask;

/**
 * 解剖任务service
 * @author 黄国刚
 *
 */
public interface TblAnatomyTaskService extends BaseDao<TblAnatomyTask> {

	/**获得下一个解剖序号
	 * @param studyNo
	 * @return
	 */
	int getNextAnatomyNumByStudyNo(String studyNo);
	
	/**保存解剖任务，解剖任务的一些字段信息从申请中获得（reqId）,
	 * 保存成功返回 "success":"true",失败返回"success":"false","msg":"该申请已创建解剖任务！"
	 * @param tblAnatomyTask仅包含创建者及是否需要复核和复核者
	 * @param reqId
	 */
	Map<String,String> save(TblAnatomyTask tblAnatomyTask, String reqId);
	
	/**判断事务是否存在
	 * @param studyNo
	 * @param reqNo
	 * @return
	 */
	boolean isExistBy(String studyNo, int reqNo) ;

	/**查询课题编号，解剖任务序号，任务id，任务创建日期（yyyy-MM-dd）；以专题启动日期为区间
	 * @param beginDateStr
	 * @param endDateStr
	 * @return
	 */
	List<Map<String, Object>> getListByStudyStartDate(String beginDateStr, String endDateStr);
	/**查询用户为病理专题负责人或者病理负责人或者专题SD的课题编号，解剖任务序号，任务id，任务创建日期（yyyy-MM-dd）；以专题启动日期为区间
	 * @param beginDateStr
	 * @param endDateStr
	 * @return
	 */
	List<Map<String, Object>> getTaskDetailListByStudyCreateDate(User user,String beginDateStr, String endDateStr);
	
	/**查询课题编号，解剖任务序号，任务id，任务创建日期（yyyy-MM-dd）,创建者（用户名—>姓名）,SD,,SD,planBeginDate(计划解剖开始日期)
	以任务创建日期为区间,任务创建时间降序排列(去除补录数据)
	 * @param beginDateStr
	 * @param endDateStr
	 * @return
	 */
	List<Map<String, Object>> getListByTaskCreateDate(String beginDateStr, String endDateStr);
	
	
	/**查询课题编号，解剖任务序号，任务id，任务创建日期（yyyy-MM-dd）,创建者（姓名）,动物种类，动物数量，解剖原因
	 *	taskId降序排列
	 * @param taskIdList2
	 * @return
	 */
	List<Map<String, Object>> getListByTaskIdList(List<String> taskIdList2);
	/**根据解剖任务id列表，查询计划需要固定的脏器的总数量
	 * @param taskList
	 * @return
	 */
	Integer getReqFixedVisceraNumberByTaskIdList(List<String> taskIdList);
	/**根据任务Id列表，查询专题编号列表
	 * @param taskIdList
	 * @return
	 */
	List<Map<String, Object>> getStudyNoByTaskIdList(List<String> taskIdList);
	
	/**
	 * 根据taskID查看已称重的动物
	 * @return
	 */
	int countAnimalWeightByTaskIdList(List<String> taskIdList);
	
	/**查询 studyNo,sd,reqDateStr,animalType animalStrain,animalNum,anatomyRsn,taskCreater,taskDateStr,reqBeginDateStr,reqEndDateStr
	 * @param taskId
	 * @return
	 */
	Map<String, Object> getMap(String taskId);
	
	/**确认签字前检查
	 * @param taskId
	 * @param itemName  解剖，固定后称重
	 * @return
	 */
	Json checkBeforeSign(String taskId, String itemName);

	/**确认签字
	 * @param taskId
	 * @param itemName  解剖，固定后称重
	 */
	void sign(String taskId, String itemName,String signer);
	
	/**检查任务下动物解剖情况
	 * @param taskId
	 * @return
	 */
	Json checkAnimalAnatomy(String taskId) ;

	/**该任务对应申请是否 含 固定后称重
	 * @param taskId
	 * @return
	 */
	boolean hasFixedWeigh(String taskId) ;

	/**该任务对应申请 是否  含固定
	 * @param taskId
	 * @return
	 */
	boolean hasFixed(String taskId) ;

	/**该任务对应申请 是否  含称重
	 * @param taskId
	 * @return
	 */
	boolean hasWeigh(String taskId) ;
	
	/**检查任务下动物脏器称重情况
	 * @param taskId
	 * @return
	 */
	Json checkVisceraWeigh(String taskId);
	
	/**检查任务下动物脏器固定情况
	 * @param taskId
	 * @return
	 */
	Json checkdVisceraFixed(String taskId) ;
	
	/**检查任务下动物脏器固定后称重情况
	 * @param taskId
	 * @return
	 */
	Json CheckVisceraFixedWeigh(String taskId);

	/**得到任务下的未解剖动物
	 * @param taskId
	 * @return
	 */
	List<Map<String, Object>> getNoAnatomyAnimal(String taskId);
	/**得到任务下的未解剖动物
	 * @param taskId
	 * @return
	 */
	List<Map<String, Object>> getNoAnatomyAnimals(String taskId);

	/** 1.解剖动物表中 visceraWeighFinishFlag == 1  或 2.visceraWeighFinishFlag == 0 ，申请无称重脏器 
	 * @param taskId
	 * @param animalCode
	 * @return
	 */
	boolean isVisceraWeighFinish(String taskId, String animalCode);

	/**创建该专题下的所有解剖任务，
	 * @param studyNo
	 * @return 
	 */
	List<Map<String, Object>> getMapListByStudyNo(String studyNo);

	/**当前任务是否有称重记录
	 * @param taskId
	 * @return
	 */
	boolean isWeightByTaskId(String taskId);

	/**查询该专题下的待选动物列表（去除1:已提交申请的对应动物列表（动物有去除的除外） 2：补录任务对应的动物列表）
	 * animalCode,gender,dosageNum,dosageDesc ，补录解剖数据专用
	 * @param studyNo
	 * @return
	 */
	List<Map<String, Object>> getNoSelectAnimalByStudyNo(String studyNo);

	/**查询该任务下已选动物列表，补录解剖数据专用,animalCode,gender,dosageNum,dosageDesc ，deadDate
	 * @param taskId
	 * @return
	 */
	List<Map<String, Object>> getSelectAnimalByTaskId(String taskId);

	/**查询解剖计划下的动物列表已选动物列表（去除1:已提交解剖申请的对应动物列表（动物有去除的除外） 2：补录任务对应的动物列表），补录解剖数据专用
	 * @param studyNo
	 * @param i
	 * @return
	 */
	List<Map<String, Object>> getSelectAnimalByStudyNoAnatomyPlanNum(String studyNo, int i);

	/**查询解剖计划列表，补录解剖数据专用
	 * @param studyNo
	 * @return
	 */
	List<Integer> getAnatomyPlanNumListByStudyNo(String studyNo);

	/**查询是否有解剖或称重信息，补录解剖数据专用(有解剖或称重信息，返回 false 及Msg  ，没有返回true)
	 * @param studyNo
	 * @param selectAnimalCodeList
	 * @return
	 */
	Json checkAnatomyOrWeigh(String studyNo, List<String> selectAnimalCodeList);

	/**保存解剖任务及对应动物
	 * @param task 缺 任务id,解剖序号，任务创建时间
	 * @param tblAnatomyAnimalList 缺Id，任务Id
	 * @return
	 */
	String saveAll(TblAnatomyTask task, List<TblAnatomyAnimal> tblAnatomyAnimalList);

	/**查询专题编号列表，有补录解剖任务的，补录解剖数据专用
	 * @return
	 */
	List<String> getStudyNoListHasAdditionalTask();

	/**查询该专题下，补录解剖任务列表（taskId,anatomyDate,animalNum,signer）
	 * @param studyNo
	 * @return
	 */
	List<Map<String, Object>> getAdditionalTaskMapListByStudyNo(String studyNo);

	/**更新解剖任务，删除原所有动物，保存新动物，补录解剖数据专用
	 * @param task
	 * @param tblAnatomyAnimalList缺Id，任务Id
	 */
	void updateAll(TblAnatomyTask task, List<TblAnatomyAnimal> tblAnatomyAnimalList);

	/**查询该专题下的待选动物列表（去除1:已提交解剖申请的对应动物列表（动物有去除的除外） 2：补录任务对应的动物列表(当前任务除外)），补录解剖数据用
	 * @param studyNo
	 * @param taskId
	 * @return
	 */
	List<Map<String, Object>> getNoSelectAnimalByStudyNoTaskId(String studyNo, String taskId);
	
	/**补录任务签字确认
	 * @param taskId
	 * @param realName
	 */
	void additionalTaskSign(String taskId, String realName);

	/**查询课题编号，解剖任务序号，任务id，任务创建日期（yyyy-MM-dd）,创建者（用户名—>姓名）,SD,,SD,planBeginDate(计划解剖开始日期)
	*,任务创建时间降序排列(去除补录数据)
	 * @param studyNo
	 * @return
	 */
	List<Map<String, Object>> getListByStudyNo(String studyNo);

	/**补录任务签字确认
	 * @param taskId
	 * @param realName
	 */
	void additionalTaskSign(String taskId, String realName,String peerRealName);

}
