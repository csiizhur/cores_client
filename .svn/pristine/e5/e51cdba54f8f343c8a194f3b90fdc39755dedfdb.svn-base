package com.lanen.service.quarantine;

import java.util.List;
import java.util.Map;

import com.lanen.base.BaseDao;
import com.lanen.model.quarantine.TblQRRequest;

public interface TblQRRequestService extends BaseDao<TblQRRequest>{

	/**
	 * 保存自己同时，保存     需求单课题编号s  和电子签名信息
	 * @param tblQRRequest
	 * @param studyNoList
	 */
	String saveOneselfAndStudyNoList(TblQRRequest tblQRRequest, List<String> studyNoList);

	/***
	 * 查询列表，根据以下条件
	 * @param startDateStr   //日期
	 * @param endDateStr     //日期
	 * @param stateInt       //状态
	 * @param animalTypeStr  //动物种类
	 * @param animalStrainStr//动物品系
	 * @return
	 */
	List<TblQRRequest> findByStateDateAnimal(String startDateStr, String endDateStr, int stateInt,
			String animalTypeStr, String animalStrainStr);

	/**
	 * 改变受理状态  ，或受理，或取消
	 * @param qrRID
	 * @param i
	 * @param realName 
	 */
	void updateAcceptStateByQRRId(String qrRID, int i, String realName);
	/**
	 * 查询   qrrId ，studyNo ,recId  列表  按  申请单号降序，课题编号升序排列（已受理的申请，未分配，未移交）
	 * @param animalTypeStr
	 * @param animalStrainStr
	 * @param animalLevelStr
	 * @return
	 */
	List<?> findByAnimalTypeStrainLevel(String animalTypeStr, String animalStrainStr,
			String animalLevelStr);

	/**
	 * 查询   qrrId ,recId  列表  按  申请单号降序，课题编号升序排列（已受理的申请，未分配，未移交）
	 * @param animalTypeStr
	 * @param animalStrainStr
	 * @param animalLevelStr
	 * @return
	 */
	List<?> findQRRIdRecIdByAnimalTypeStrainLevel(String animalTypeStr, String animalStrainStr,
			String animalLevelStr);

	/**
	 * 查询studyNo 列表 ，（已受理的申请，未移交 ,接收编号为空 或”“  ，或等于这个）
	 * @param animalType
	 * @param animalStrain
	 * @param animalLevel
	 * @param recId    接收编号为空 或”“  ，或等于这个
	 * @return
	 */
	List<String> getStudyNoListByAnimalRecId(String animalType, String animalStrain,
			String animalLevel, String recId);
	/**
	 * 更新分配状态为   1，根据接收单号和课题编号数组
	 * @param recId
	 * @param studyNos
	 */
	void updatePlanState(String recId, String[] studyNos);

	/**
	 * 更新分配状态为   1，根据接收单号 和   课题编号
	 * @param recId
	 * @param studyNos
	 */
	void updatePlanStateToOne(String recId,String studyNo);

	/**
	 * 更新分配状态，先判断该为0还是不变
	 * @param studyNo
	 */
	void updatePlanStateToOneOrZero(String studyNo);

	/**
	 * 查询studyNo 列表 ，（已受理的申请 ，已分配     ， 未移交,且课题编号列表内状态也      未移交)
	 * @param animalType
	 * @return
	 */
	List<String> getStudyNoListByAnimal(String animalType);

	String saveOneselfAndStudyNoMapList(TblQRRequest tblQRRequest,
			List<Map<String, String>> studyNoMapList);

	/**  查询 qrId  ， 已受理未移交
	 * @param animalType
	 * @param animalStrain
	 * @param animalLevel
	 * @return
	 */
	List<String> getQrIdListByAnimal(String animalType, String animalStrain, String animalLevel);

	/**更新申请单及对应所有课题  的移交状态置为i(0,1)
	 * @param qrRId
	 * @param i
	 */
	void updateTransferStateByQRRId(String qrRId, int i);
	
}
