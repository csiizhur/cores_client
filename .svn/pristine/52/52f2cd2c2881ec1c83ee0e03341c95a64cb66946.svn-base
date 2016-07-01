package com.lanen.service.path;

import java.util.List;
import java.util.Map;

import com.lanen.base.BaseDao;
import com.lanen.jsonAndModel.Json;
import com.lanen.model.path.TblAnimalTargetOrgan;

/**试验靶器官登记表 service
 * @author Administrator
 *
 */
public interface TblAnimalTargetOrganService extends BaseDao<TblAnimalTargetOrgan>{

	/**获取任务对应申请的试验阶段
	 * @param taskId
	 * @return
	 */
	String getTestPhaseByTaskId(String taskId);

	/**加载脏器列表（可以添加靶器官（发生）的列表）  tsv.visceraCode,tsv.visceraName,tsv.visceraType,dv.sn
	 * @param studyNo
	 * @return
	 */
	List<Map<String, Object>> getfsMapListByStudyNo(String studyNo);
	
	/**加载脏器列表（可以添加靶器官（消失）的列表）  tsv.visceraCode,tsv.visceraName,tsv.visceraType,dv.sn
	 * @param studyNo
	 * @return
	 */
	List<Map<String, Object>> getMapListByStudyNo(String studyNo);

	/**增加一个实体，并保存签字
	 * @param tblAnimalTargetOrgan
	 * @param operator
	 */
	void addOne(TblAnimalTargetOrgan tblAnimalTargetOrgan, String operator);

	/**获取最新的靶器官 对应的性别
	 * @param studyNo
	 * @param newValue
	 * @return
	 */
	int getGenderByStudyNoVisceraName(String studyNo, String newValue);

	/**查询专题下多有靶器官（包括删除的）（）
	 * @param studyNo
	 * @return
	 */
	List<Map<String,Object>> getListByStudyNo(String studyNo);

	/** 删除，保存删除原因并签字
	 * @param id
	 * @param reason
	 * @param realName
	 * @return
	 */
	Json delOne(String id, String reason, String realName);

	/**查询当前脏器最后登记的信息（删除的除外）
	 * @param studyNo
	 * @param visceraName
	 * @return
	 */
	TblAnimalTargetOrgan getLastTargetOrgan(String studyNo, String visceraName);

	/**查询当前靶器官是否有比当前id，还新的数据
	 * @param id
	 * @return
	 */
	boolean isHasNewRecord(String id);

}
