package com.lanen.service.path;

import java.util.List;
import java.util.Map;

import com.lanen.base.BaseDao;
import com.lanen.model.path.TblPathPlanAttachedViscera;
import com.lanen.model.path.TblPathPlanVisceraWeigh;
/**
 * 病理计划-脏器称重     service 
 * @author 曾锋
 */
public interface TblPathPlanVisceraWeighService extends BaseDao<TblPathPlanVisceraWeigh> {

	/**根据课题编号查找当前课题下的脏器称重计划记录
	 * @param studyNoPara(课题编号) 
	 * @return
	 */
	List<TblPathPlanVisceraWeigh> getListByStudyNo(String studyNoPara);

	/**数据库获得Sn(当前课题下最大Sn+1)
	 * @param studyNo (课题编号) 
	 * @return
	 */
	int getSn(String studyNo);

	/**根据脏器编号及课题编号查询实体
	 * @param visceraCode
	 * @param studyNo
	 * @return
	 */
	TblPathPlanVisceraWeigh getByVisceraCode(String visceraCode,String studyNo);

	/**加载脏器列表，根据课题编号和动物类型
	 * @param animalTypeId
	 * @param studyNoPara
	 * @return
	 */
	List<Map<String, Object>> getVisceraListByAnimalTypeAndStudyNo(
			String animalTypeId, String studyNoPara);

	/**添加保存病理计划-脏器称重
	 * @param list
	 * @param attachedList
	 */
	void addSavePathPlanVisceraWeigh(List<TblPathPlanVisceraWeigh> list,
			List<TblPathPlanAttachedViscera> attachedList,String studyNo);

}
