package com.lanen.service.path;

import java.util.List;
import java.util.Map;

import com.lanen.base.BaseDao;
import com.lanen.model.path.TblAnatomyReqPathPlanCheck;
/**
 * 解剖申请-脏器/组织学检查   service
 *@author 
 */
public interface TblAnatomyReqPathPlanCheckService extends BaseDao<TblAnatomyReqPathPlanCheck> {

	/**根据课题编号和申请编号得到Sn(当前最大Sn+1)
	 * @param studyNoPara
	 * @param reqNo(申请编号)
	 * @return
	 */
	int getSn(String studyNoPara, Integer reqNo);

	/**根据课题编号和申请编号查询解剖申请-脏器/组织学检查
	 * @param studyNoPara
	 * @param reqNo
	 * @return
	 */
	List<TblAnatomyReqPathPlanCheck> getListByStudyNoAndReqNo(
			String studyNoPara, int reqNo);
	/**加载脏器列表，根据动物类型和课题编号
	 * @param animalTypeId
	 * @param studyNoPara
	 * @return
	 */
	List<Map<String, Object>> getVisceraListByAnimalTypeAndStudyNo(
			String animalTypeId, String studyNoPara,Integer reqNo);
}
