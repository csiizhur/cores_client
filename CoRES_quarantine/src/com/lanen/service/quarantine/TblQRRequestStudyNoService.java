package com.lanen.service.quarantine;

import java.util.List;
import java.util.Map;

import com.lanen.base.BaseDao;
import com.lanen.model.quarantine.TblQRRequest;
import com.lanen.model.quarantine.TblQRRequestStudyNo;

public interface TblQRRequestStudyNoService extends BaseDao<TblQRRequestStudyNo>{

	/**
	 * 保存多个实体
	 * @param studyNoList
	 * @param tblQRRequest
	 */
	void saveMore(List<String> studyNoList, TblQRRequest tblQRRequest);

	/**
	 * 根据申请单号查询课题编号列表
	 * @param qrRID
	 * @return
	 */
	String getStudyNoListStrByQRRId(String qrRID);

	/**
	 * 根据接收单号查询课题编号列表
	 * @param qrRID
	 * @return
	 */
	List<String> getStudyNoListByRecId(String recId);

	/**
	 * 根据接收单号查询  课题编号与申请单号的键值对
	 * @param recId
	 * @return
	 */
	Map<String, String> getStudyNoQRRIdMapByRecId(String recId);

	/**
	 * 根据课题编号查询是否存在
	 * @param studyNo
	 * @return
	 */
	boolean isExistByStudyNo(String studyNo);

	/** 查询 tblQRRequestStudyNo as a ,tblQRRequest as b ,a未提交      b已受理
	 * @param animalType
	 * @param animalStrain
	 * @param animalLevel
	 * @return
	 */
	List<String> getStudyNoListByAnimal(String animalType, String animalStrain, String animalLevel);

	/**根据课题编号查询对应的申请单号
	 * @param studyNo
	 * @return
	 */
	String getQrIdByStudyNo(String studyNo);

	/**
	 * 根据申请单号查询课题编号列表,返回的是list《Stirng》
	 * @param qrRID
	 * @return
	 */
	List<String> getStudyNoListsStrByQRRId(String qrId);

	/**根据课题查询实体
	 * @param studyNo
	 * @return
	 */
	TblQRRequestStudyNo getByStudyNo(String studyNo);

	/**  申请单课题编号，移交状态置为  i
	 * @param studyNo
	 * @param i
	 */
	void updateTransferStateByStudyNo(String studyNo, int i);

	/**判断申请单下课题是否都完成，
	 * @param qrRId
	 * @return
	 */
	boolean isAllStudyNoFinishByQrRId(String qrRId);

}
