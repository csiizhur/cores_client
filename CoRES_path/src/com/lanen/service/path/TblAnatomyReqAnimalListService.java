package com.lanen.service.path;

import java.util.List;
import java.util.Map;

import com.lanen.base.BaseDao;
import com.lanen.model.path.TblAnatomyReqAnimalList;

/**
 * 申请解剖动物列表   service
 * @author 
 */
public interface TblAnatomyReqAnimalListService extends BaseDao<TblAnatomyReqAnimalList> {

	/**根据课题编号和申请编号查询动物
	 * @param studyNoPara()
	 * @param AnatomyReqNo()
	 * @return
	 */
	int getAnimalNumberByStudyNo(String studyNoPara, int anatomyReqNo);
	
	/**
	 * 根据任务Id查询动物数量
	 * @param taskIdLists
	 * @return
	 */
	int getAnimalNumberByTaskIdList(List<String> taskIdLists);
	
	
	/**
	 * 根据任务ID查询动物
	 * @param sessionID
	 * @return
	 */
	List<Map<String,Object>> getAnimalCodeByTaskIdList(List<String> taskIdLists);
	
	
	/**
	 * 根据专题编号查询动物
	 * @param sessionID
	 * @return
	 */
	List<Map<String,Object>> getAnimalCodeByStudyNo(String studyNo, List<String> sessionIdList);
	
	
	/**查询课题下动物是否已被申请解剖（解剖申请提交标志变为大于0），如果已存在返回1，不存在返回0
	 * @param studyNo 
	 * @param animalCode
	 * @return
	 */
	int isHaveAnatomyReq(String studyNo,String animalCode );

	/**查询课题下所有可以被申请解剖的动物（已被申请(申请已提交的)的不能被再申请）
	 * @param studyNoPara
	 * @return
	 */
	List<?> getAllAnimalByStudyNo(String studyNoPara);

	/**根据课题编号和解剖申请编号查询申请解剖动物列表
	 * @param studyNoPara
	 * @param anatomyReqNo
	 * @return
	 */
	List<TblAnatomyReqAnimalList> getListByStudyNoAndReqNo(String studyNoPara,
			int anatomyReqNo);

	/**查询实体
	 * @param studyNo
	 * @param reqNo
	 * @param animalCode
	 */
	TblAnatomyReqAnimalList getByStudyNoReqNoAnimalCode(String studyNo, Integer reqNo,
			String animalCode);

	/**根据课题编号和申请编号查询已被申请提交的动物数量（提交标志>0）
	 * @param studyNo
	 * @param anatomyReqNo
	 * @return
	 */
	Integer getAnimalNumberByStudyNoAndReqNo(String studyNo, int anatomyReqNo);

	

}
