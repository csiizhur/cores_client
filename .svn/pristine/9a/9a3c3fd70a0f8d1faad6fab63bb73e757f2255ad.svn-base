package com.lanen.service.path;

import java.util.List;

import com.lanen.base.BaseDao;
import com.lanen.jsonAndModel.Json;
import com.lanen.model.path.TblDeadAnimalDeathReason;

/**死亡动物致死原因记录
 * @author Administrator
 *
 */
public interface TblDeadAnimalDeathReasonService extends BaseDao<TblDeadAnimalDeathReason> {

	/**判断是否有致死原因记录（当前动物的，删除的也算）
	 * @param studyNo
	 * @param animalCode
	 * @return
	 */
	boolean isHasRecord(String studyNo, String animalCode);

	/**保存致死原因，并保存签字
	 * @param tblDeadAnimalDeathReason
	 * @param operator
	 */
	void addOne(TblDeadAnimalDeathReason tblDeadAnimalDeathReason, String operator);

	/**查询致死原因列表
	 * @param studyNo
	 * @param animalCode
	 * @return
	 */
	List<TblDeadAnimalDeathReason> getListByStudyNoAnimalCode(String studyNo, String animalCode);

	/**删除致死原因列表，并保存
	 * @param id
	 * @param reason
	 * @param realName
	 * @return
	 */
	Json delOne(String id, String reason, String realName);

	/**获取死亡原因
	 * @param studyNo
	 * @param animalCode
	 * @return
	 */
	String getDeathReason(String studyNo, String animalCode);

}
