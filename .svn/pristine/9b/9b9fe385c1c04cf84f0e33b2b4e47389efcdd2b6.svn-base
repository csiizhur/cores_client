package com.lanen.service.path;

import java.util.List;
import java.util.Map;

import com.lanen.base.BaseDao;
import com.lanen.model.path.TblAnatomyReqVisceraWeigh;

/**
 * 解剖申请-脏器称重   service
 *@author 
 */
public interface TblAnatomyReqVisceraWeighService extends BaseDao<TblAnatomyReqVisceraWeigh> {

	/**根据课题编号和申请编号得到Sn(当前最大Sn+1)
	 * @param reqNo (申请编号)
	 * @param studyNoPara（课题编号）
	 * @return
	 */
	int getSn(String studyNoPara, Integer reqNo);

	/**根据课题编号和申请编号查询解剖申请-脏器称重
	 * @param reqNo （申请编号）
	 * @param studyNoPara（课题编号） 
	 * @return
	 */
	List<TblAnatomyReqVisceraWeigh> getListByStudyAndReqNo(String studyNoPara, int reqNo);
	/**加载脏器列表，根据动物类型和课题编号
	 * @param animalTypeId
	 * @param studyNoPara
	 * @return
	 */
	List<Map<String, Object>> getVisceraListByAnimalTypeAndStudyNo(
			String animalTypeId, String studyNoPara,Integer reqNo);
	
}
