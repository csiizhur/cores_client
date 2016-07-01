package com.lanen.service.path;

import java.util.List;
import java.util.Map;

import com.lanen.base.BaseDao;
import com.lanen.jsonAndModel.Json;
import com.lanen.model.path.TblAnatomyCheckEdit;

/**解剖所见数据修改service
 * @author Administrator
 *
 */
public interface TblAnatomyCheckEditService extends BaseDao<TblAnatomyCheckEdit>{

	/**查询解剖所见和修改后的结果
	 * @return
	 */
	List<Map<String,Object>> getMapListByTaskId(String taskId);

	/**保存添加信息  
	 * @param tblAnatomyCheckEdit
	 * @return
	 */
	Json saveOne(TblAnatomyCheckEdit tblAnatomyCheckEdit);

	/**编辑  保存信息
	 * @param tblAnatomyCheckEdit
	 * @param currentUserName
	 * @return
	 */
	Json saveOne(TblAnatomyCheckEdit tblAnatomyCheckEdit, String currentUserName);

	/**删除解剖所见或删除解剖所见编辑
	 * @param id
	 * @param checkId
	 * @param currentUserName
	 * @param reason
	 */
	void deleteOne(String id, String checkId, String currentUserName, String reason,String taskId);

	/**查询 动物解剖修改记录  数据（打印  editType animalCode visceraName anatomyCheckResult anatomyCheckEditResult）
	 * @param taskId
	 * @return
	 */
	List<Map<String, Object>> getAnatomyCheckEditPrint(String taskId);

	/**保存解剖数据修改是，同时添加固定数据修改
	 * @param tblAnatomyCheckEdit
	 * @return
	 */
	Json saveOne_1(TblAnatomyCheckEdit tblAnatomyCheckEdit);
}
