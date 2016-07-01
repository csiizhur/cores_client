package com.lanen.service.clinicaltest;

import java.util.List;

import com.lanen.base.BaseDao;
import com.lanen.model.clinicaltest.TblSpecimen;
import com.lanen.model.studyplan.TblClinicalTestReq;

public interface TblSpecimenService extends BaseDao<TblSpecimen>{

	/**
	 * 保存标本列表，并保存签名记录
	 * @param list
	 * @param realName
	 */
	void saveList(List<TblSpecimen> list, String realName);

	/**
	 * 得到尿液检查的当天的   最大编号
	 * @param testItem
	 * @param date
	 * @return
	 */
	String getToDayMostCode();

	/**
	 * 查询所有按  接收时间降序排列
	 * @return
	 */
	List<TblSpecimen> findAllOrderByRecdateDese();

	/**
	 * 查询所有试验计划
	 * @return
	 */
	List<com.lanen.model.studyplan.TblStudyPlan> findAllTblStudyPlan();

	/**
	 * 根据条件查询, 按专题编号，申请编号，检测项目，排序
	 * @param studyNo  课题编号
	 * @param reqNo    申请编号
	 * @param animalId 动物id号
	 * @param testItemString    检测项目
	 * @param specimenCode    检验编号
	 * @param recDate    接收日期
	 * @return
	 */
	List<TblSpecimen> findByCondition(String studyNo, String reqNo, String animalId,
			String testItemString, String specimenCode, String recDate);

	/**
	 * 查询该申请下的所有标本接收记录
	 * @param tblClinicalTestReq2
	 * @return
	 */
	List<TblSpecimen> findByTblClinicalTestReq(TblClinicalTestReq tblClinicalTestReq2);

	/**
	 * 根据    临检编号     检测项目     检测完成日期   查询
	 * @param specimenCode
	 * @param testItem
	 * @param collectionTime
	 * @return
	 */
	TblSpecimen getBySpecimenCodeTestItemTime(String specimenCode,
			int testItem, String collectionTime);
	
	/**
	 * 根据  表格中信息  查询该标本的Id   更具Id判断该标本是否检测，即该标本是否有检测结果数据
	 * @param studyNo
	 * @param reqNo
	 * @param animalId
	 * @param recDate  
	 * @param specimenCode
	 * @return
	 */
	boolean isExistBySpecimen(String studyNo, int reqNo, String animalId, String recDate,
			String specimenCode);

	/**
	 * 根据  表格中信息  查询该标本的数据Id   
	 * @param studyNo
	 * @param reqNo
	 * @param animalId
	 * @param recDate
	 * @param specimenCode
	 * @return
	 */
	String getIdBySpecimen(String studyNo, int reqNo, String animalId, String recDate,
			String specimenCode);

	/**
	 * 判断该申请下有没有标本接收记录
	 * @param tblClinicalTestReq
	 * @return 
	 */
	boolean isExistByClinicalTestReq(TblClinicalTestReq tblClinicalTestReq);

	/**
	 * 判断该申请下 该动物   有没有标本接收记录
	 * @param tblClinicalTestReq
	 * @return 
	 */
	boolean isExistByClinicalTestReqAndAnimalId(TblClinicalTestReq tblClinicalTestReq,
			String selected);

	/** 查询专题编号列表  ，接收日期
	 * @param recDate  yyyy-MM-dd
	 * @return
	 */
	List<String> getStudyNoListByRecDate(String recDate);

	/**根据条件查询 studyNo,animalId,animalCode,recTime,testItem specimenCode,receiver(接收人)，送检人sender 
	 * , 按    接收时间，检测项目，检验编号 排序
	 * @param studyNo
	 * @param reqNo
	 * @param animalId
	 * @param testItemString
	 * @param specimenCode
	 * @param recDate
	 * @return
	 */
	List<?> findList(String studyNo, String reqNo, String animalId, String testItemString,
			String specimenCode, String recDate);

	/** 查询接收日期列表     yyyy-MM-dd
	 * @param studyNo
	 * @param reqNo
	 * @param testItem
	 * @return
	 */
	List<String> getRecDateStrList(String studyNo, String reqNo, int testItem);

	/**
	 * 保存标本列表，并保存签名记录
	 * @param list
	 * @param realName  标本接收人
	 * @param realName2   送检人
	 */
	void saveList(List<TblSpecimen> list, String realName,String realName2);

	/**查询该申请下的标本接收记录（studyNo,reqNo,animalId,animalCode,recDate
	 * 				,recTime,testItem,specimenCode,specimenKind,sender,receiver） 
	 * 				按    接收时间，检测项目，检验编号 排序
	 * @param tblClinicalTestReq
	 * @return
	 */
	List<?> findListByTblClinicalTestReq(TblClinicalTestReq tblClinicalTestReq);

	/**查询该申请下的标本接收记录（studyNo,reqNo,animalId,animalCode,recDate
	 * 				,recTime,testItem,specimenCode,specimenKind,sender,receiver） 
	 * 				按    接收时间，检测项目，检验编号 排序
	 * @param studyNo2
	 * @param reqNo2
	 * @param animalId2
	 * @param testItemString2
	 * @param specimenCode2
	 * @param recDate2
	 * @return
	 */
	List<?> findListByCondition(String studyNo2, String reqNo2, String animalId2,
			String testItemString2, String specimenCode2, String recDate2);
	

}
