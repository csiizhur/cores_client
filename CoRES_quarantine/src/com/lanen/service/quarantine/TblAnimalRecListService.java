package com.lanen.service.quarantine;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lanen.base.BaseDao;
import com.lanen.model.quarantine.TblAnimalRecList;

public interface TblAnimalRecListService extends BaseDao<TblAnimalRecList> {

	/***
	 * 保存（多个）实体 
	 * @param tblAnimalRecLists
	 */
	void saveList(List<TblAnimalRecList> tblAnimalRecLists);

	/***
	 * 根据接收单号  ，查询实体列表
	 * @param recId
	 * @return
	 */
	List<TblAnimalRecList> getListByRecId(String recId);

	/**
	 * 判断该动物Id号   在该接收单号下是否存在
	 * @param recId    接收单号
	 * @param animalId 动物Id号
	 * @return
	 */
	boolean isExistByRecIdAnimalId(String recId, String animalId);

	/**
	 * 更新实体列表
	 * @param tblAnimalRecLists
	 */
	void updateList(List<TblAnimalRecList> tblAnimalRecLists);

	/***
	 * 根据接收单号  ，查询实体列表（课题编号为空或‘’）
	 * @param recId
	 * @return
	 */
	List<TblAnimalRecList> getNoStudyNoListByRecId(String recId);

	/**
	 * 根据课题编号  ，查询实体列表
	 * @param studyNo
	 * @return
	 */
	List<TblAnimalRecList> getListByStudyNo(String studyNo);

	/**
	 * 更新课题编号及申请编号，Id in  idList
	 * @param idList
	 * @param studyNo
	 */
	void updateList(List<String> idList, String studyNo);

	/***
	 * 根据接收单号  ，查询实体列表, 且是未死亡，未移交，未备库
	 * @param recId
	 * @return
	 */
	List<TblAnimalRecList> getListByRecIdAndState(String recId);

	/**
	 * 根据课题编号  ，查询实体列表, 且是未死亡，未移交，未备库
	 * @param studyNo
	 * @return
	 */
	List<TblAnimalRecList> getListByStudyNoAndState(String studyNo);

	
	/**判断动物Id号是否是一个未录入（当前接单下）
	 * @param recId
	 * @return
	 */
	boolean isNoAnimalId(String recId);

	/**查询下一个动物Id号，（特指小动物当中的）
	 * @return
	 */
	String getNextAnimalId();

	/**判断动物id号是否存在
	 * @param animalId
	 * @return
	 */
	boolean isExistByAnimalId(String animalId);

	/**非  idList中，查询存在的动物id号，返回存在的动物id号列表字符串
	 * @param animalIdList
	 * @param idList
	 * @return
	 */
	String isExistByAnimalIdListAndIdList(List<String> animalIdList, List<String> idList);

	/**根据动物Id号  查询recId
	 * @param animalId
	 * @return
	 */
	String getRecIdByAnimalId(String animalId);

	

	/**更新接收动物死亡状态
	 * @param deadFlag 死亡状态   0,1
	 * @param deadDate 死亡日期
	 * @param deadRsn  死亡原因
	 * @param deadOperator  登记死亡操作者
	 * @param deadRegTime  登记死亡时间
	 * @param animalIdList 动物id号    列表
	 */
	void updateAnimalDeadState(int deadFlag, Date deadDate, String deadRsn, String deadOperator,
			Date deadRegTime, List<String> animalIdList);

	/**更新room  ,
	 * @param room
	 * @param animalIdList  动物id号    列表
	 */
	void updateAnimalRoom(String room, List<String> animalIdList);

	/** 判断是否是备库动物
	 * @param animalId
	 * @return
	 */
	boolean isBeikuByAnimalId(String animalId);

	/** 查询当前申请单(移交到该申请单下的课题也算)，     已经移交的动物数量     map<"male",80>  或者map<"female",80>
	 * @param qrRId
	 * @param studyNoList
	 * @return
	 */
	Map<String, Integer> getFinishNumberMapBy(String qrRId, List<String> studyNoList);

	/**查询当前课题     已经移交的动物数量     map<"male",80>  或者map<"female",80>
	 * @param studyNo
	 * @return
	 */
	Map<String, Integer> getStudyFinishNumberMapByStudyNo(String studyNo);

	/**更新接收动物列表中对应的  备库、进入备库时间、 移交标志、移交接收人、移交时间、
	 *   移交申请单号、移交课题编号   、移交后房间
	 * @param animalIdList 动物Id列表
	 * @param reserveFlag
	 * @param reserveDate
	 * @param transFlag
	 * @param applicant
	 * @param beginDate
	 * @param qrRId
	 * @param studyNo
	 * @param room
	 */
	void updateAnimalHandover(List<String> animalIdList, int reserveFlag, Date reserveDate,
			int transFlag, String applicant, Date beginDate, String qrRId, String studyNo,
			String room);

}
