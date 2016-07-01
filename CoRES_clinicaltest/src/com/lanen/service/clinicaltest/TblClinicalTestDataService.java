package com.lanen.service.clinicaltest;

import java.util.List;
import java.util.Map;

import com.lanen.base.BaseDao;
import com.lanen.model.clinicaltest.TblClinicalTestData;
import com.lanen.model.clinicaltest.TblDataSource;
import com.lanen.model.clinicaltest.TblSpecimen;
import com.lanen.model.clinicaltest.TblTrace;
import com.lanen.model.studyplan.TblClinicalTestReq;

public interface TblClinicalTestDataService extends BaseDao<TblClinicalTestData> {

	/**
	 * 获取   某天   中的所有 有检测数据的    试验计划编号列表
	 * @param date
	 * @return
	 */
	List<String> getStudyPlanByDate(String date);

	/**
	 * 获取   某天   中的所有 有检测数据
	 * @param date
	 * @return
	 */
	List<TblClinicalTestData> findByDate(String date);

	/**
	 * 保存实体（检验编号，检测项目，设备Id，通道号，数据，单位，检测完成时间,数据源信息）
	 * @param specimenCode
	 * @param testItem
	 * @param passageway
	 * @param testData
	 * @param testIndexUnit
	 * @param collectionTime
	 * @param tblDataSource 
	 * @return     返回  保存的实体
	 */
	TblClinicalTestData saveBySpecimenCodeTestItemPassageway(String specimenCode, int testItem,String instrumentId
			,String passageway, String testData, String testIndexUnit, String collectionTime, TblDataSource tblDataSource);

	/**
	 *  该日期      es  下的   临检数据
	 * @param date
	 * @param es
	 * @return
	 */
	List<TblClinicalTestData> findByDateES(String date, int es);

	/**
	 * 该日期      es   检测项目      下的   临检数据
	 * @param date
	 * @param testItem
	 * @param es
	 * @return
	 */
	List<TblClinicalTestData> findByDateTestItemES(String date, int testItem, int es);

	/**
	 * 保存签名信息
	 * @param list
	 * @param realName
	 */
	void es(List<String> list, String realName);

	/**
	 * 根据  课题编号  ，申请编号  ，检测项目查询
	 * @param string
	 * @param reqNo
	 * @param testItem
	 * @return
	 */
	List<TblClinicalTestData> findByStudyNoReqNoTestItem(String studyNo, int reqNo, int testItem);
	
	/**
	 * 根据  课题编号  ，申请编号  ，检测项目查询
	 * @param string
	 * @param reqNo
	 * @param testItem
	 * @return
	 */
	List<TblClinicalTestData> findByStudyNoReqNoTestItemAnimalCode(String studyNo, int reqNo, int testItem,String animalId);
	/**
	 * 根据课题编号 检测项目动物Id查询
	 * @param studyNo
	 * @param testItem
	 * @param animalId
	 * @return
	 */
	List<TblClinicalTestData> findByStudyNoTestItemAnimalCode(String studyNo, int testItem,String animalId);

	/**
	 * 根据临检申请和检测项目  查询检测时期和签字人（检验人）
	 * @param tblClinicalTestReq
	 * @param testItem
	 * @return
	 */
	Map<String, Object> getMapbyClinicalTestReqAndTestItem(TblClinicalTestReq tblClinicalTestReq,
			int testItem);
	/**
	 * 根据临检申请\检测项目和动物Id号   查询检测时期和签字人（检验人）
	 * @param tblClinicalTestReq
	 * @param testItem
	 * @return
	 */
	Map<String, Object> getMapbyClinicalTestReqAndTestItemAndAnimalId(
			TblClinicalTestReq tblClinicalTestReq, int testItem, String animalId);
//	/**
//	 * 保存所有实体（检验编号，检测项目，设备Id，检测完成时间,数据源信息，list{通道号，数据，单位}）
//	 * map中有   msgList:List<String> tblClinicalTestDataList:List<TblClinicalTestData>
//	 * @param specimenCode
//	 * @param testItem
//	 * @param passageway
//	 * @param testData
//	 * @param testIndexUnit
//	 * @param collectionTime
//	 * @param tblDataSource 
//	 * @return     返回  保存的所有实体
//	 */
//	Map<String,Object> saveAllBySpecimenCodeTestItem(String specimenCode, int testItem,
//			String instrumentId, String collectionTime, TblDataSource tblDataSource,
//			List<String[]> list);
	/**
	 * 保存所有实体（检验编号，检测项目，设备Id，检测完成时间,数据源信息，list{通道号，数据，单位}）
	 * map中有   msgList:List<String> tblClinicalTestDataList:List<TblClinicalTestData>
	 * @param specimenCode
	 * @param testItem
	 * @param passageway
	 * @param testData
	 * @param testIndexUnit
	 * @param collectionTime
	 * @param tblDataSource 
	 * @return     返回  保存的所有实体
	 */
	List<TblClinicalTestData> saveAllBySpecimenCodeTestItem(String specimenCode, int testItem,
			String instrumentId, String collectionTime, TblDataSource tblDataSource,
			List<String[]> list);

	/**保存所有实体（检验编号，检测项目未血凝（3），设备Id，数据源信息，list{通道号，数据，单位，检测时间}）
	 * @param specimenCode
	 * @param instrumentId
	 * @param tblDataSource
	 * @param list
	 * @return
	 */
	List<TblClinicalTestData> saveAllBySpecimenCodeTestItem_3(String specimenCode,
			String instrumentId, TblDataSource tblDataSource, List<String[]> list);
	
	/**
	 * 根据  课题编号  ，申请编号 查询list
	 * @param string
	 * @param reqNo
	 * @param testItem
	 * @return
	 */
	List<TblClinicalTestData> findByStudyNoReqNo(String studyNo, int reqNo);
	/**
	 * 根据  课题编号  ，申请编号  ，检测项目, 是否签字    查询
	 * @param string
	 * @param reqNo
	 * @param testItem
	 * @return
	 */
	List<TblClinicalTestData> findByStudyNoReqNoTestItem(String studyNoPara,
			int reqNoPara, int testItemPara, int es);
	/**
	 * 查询   某天  数据中的        课题编号：list<临检申请>
	 * @param dateStr
	 * @return
	 */
	Map<String, List<String>> getMapByDate(String dateStr);
	
	/**
	 * 查询数据
	 * @param date     //日期
	 * @param testItem //检测项目
	 * @param es       //是否签字
	 * @param studyNo2 //课题编号
	 * @param reqNo2   //临检申请编号
	 * @param beginCodeStr2//检验编号
	 * @param endCodeStr2 //检验编号
	 * @return
	 */
	List<TblClinicalTestData> findByDateTestItemESStudyNOReqNoCode(String date, int testItem,
			int es, String studyNo2, int reqNo2, String beginCodeStr2, String endCodeStr2);
	
	
	/**
	 * 根据接收标本，指标缩写，查询临检数据列表
	 * @param tblSpecimen
	 * @param indexAbbr
	 * @return
	 */
	public List<TblClinicalTestData> getListByTblSpecimenAbbr(
			TblSpecimen tblSpecimen, String indexAbbr) ;
	
	/**
	 * 数据签字确认，并设置数据的有效性
	 * @param list
	 * @param realName
	 * @param validDataIdList
	 * @param inValidDataIdList
	 */
	void esAndSetValid(List<String> list, String realName, List<String> validDataIdList,
			List<String> inValidDataIdList);
	
	
	/**
	 * 根据    查询临检数据列表
	 * @param studyNo
	 * @param reqNo
	 * @param testItem
	 * @param dataIdList
	 * @return
	 */
	List<TblClinicalTestData> getListByStudyNoReqNoTestItemDataIdList(String studyNo, int reqNo,
			int testItem, List<String> dataIdList);
	
	/**
	 * 删除并记录修改痕迹
	 * @param list
	 * @param realName
	 * @param reason
	 */
	void deleteAndRecordTrace(List<String> list, String realName, String reason,String hostName);
	/**
	 * 查询该指标的检测结果数据中，是否存在已签字且是有效的数据
	 * @param specimenCode
	 * @param testItem
	 * @param testIndexAbbr
	 * @param reqNo 
	 * @param studyNo 
	 * @return
	 */
	boolean isHaveValidData(String specimenCode, int testItem, String testIndexAbbr, String studyNo, String reqNo);
	
	void updateAllTblClinicalTestData(List<TblClinicalTestData> list);
	
	/**根据  专题编号和申请编号和检测项目(es = 1)，查询  检测日期列表    yyyy-MM-dd
	 * @param studyNo
	 * @param reqNo
	 * @param testItem 
	 * @return
	 */
	List<String> getDateStrListByStudyNoReqNo(String studyNo, String reqNo, int testItem);
	
	
	/** 查询动物animalId,animalCode,根据  专题、申请编号，检测项目、日期列表(es = 1)
	 * @param studyNo
	 * @param reqNo
	 * @param testItem
	 * @param selectedDateStrList
	 * @return
	 */
	List<?> getAnimalList(String studyNo, String reqNo, int testItem,
			List<String> selectedDateStrList);
	
	/** 查询符合要求的检测数据
	 * @param studyNo
	 * @param reqNo
	 * @param testItem
	 * @param animalIdList
	 * @param dateStrList
	 * @return
	 */
	List<TblClinicalTestData> getListByStudyNoReqNoTestItemAnimalIdList(String studyNo,
			String reqNo, int testItem, List<String> animalIdList, List<String> dateStrList);
	
	/**是否有签过字的检测数据
	 * @param studyNo
	 * @param reqNo
	 * @return
	 */
	boolean isHasESData(String studyNo, String reqNo);
	
	
	/**查询标本接收日期列表
	 * @param studyNo
	 * @param reqNo
	 * @param testItem
	 * @param testDate   检测日期   yyyy-MM-dd
	 * @return
	 */
	List<String> getSpecimenRecDateStrList(String studyNo, String reqNo, int testItem,
			String testDate);

	/** 查询检测日期列表
	 * @param studyNo
	 * @param reqNo
	 * @param testItem   检测项目
	 * @param specimenRecDate  样本接收日期  yyyy-MM-dd
	 * @return
	 */
	List<String> getTestDateStrList(String studyNo, String reqNo, int testItem,
			String specimenRecDate);
	
	
	/**查询 检验编号、动物号、性别、动物Id号    List列表
	 * @param studyNo 专题编号
	 * @param reqNo   临检申请编号
	 * @param testItem检测项目      1,2,3,4
	 * @param specimenRecDate   标本接收日期    yyyy-MM-dd
	 * @param testDate     检测日期     yyyy-MM-dd
	 * @return
	 */
	List<?> get4StrList(String studyNo, String reqNo, int testItem, String specimenRecDate,
			String testDate);
	
	
	/**查询 检验编号、动物号、性别、动物Id号 ,检验指标缩写，检测结果，排序aniSerialNum ,标本类型,检验指标单位  List列表
	 * @param studyNo 专题编号
	 * @param reqNo   临检申请编号
	 * @param testItem检测项目      1,2,3,4
	 * @param specimenRecDate   标本接收日期    yyyy-MM-dd
	 * @param testDate     检测日期     yyyy-MM-dd
	 * @param animalIdList
	 * @return
	 */
	List<?> get9StrListByStudyNoReqNoTestItemAnimalIdList(String studyNo, String reqNo,
			int testItem, String specimenRecDate, String testDate, List<String> animalIdList);
	
	

	/**   查询数据库得到   检验指标、动物编号、动物Id号、检验项目、专题编号   列表 ，按检测项目   、检验编号排序
	 * @param date
	 * @param testItem
	 * @param es
	 * @param studyNo2
	 * @param reqNo2
	 * @param beginCodeStr2
	 * @param endCodeStr2
	 * @return
	 */
	List<?> findListByDateTestItemESStudyNOReqNoCode(String date, int testItem, int es,
			String studyNo2, int reqNo2, String beginCodeStr2, String endCodeStr2);
	
	/**获取   confirmFlag  值
	 * @param thisSpecimenCode
	 * @param thisTestItem
	 * @param string
	 * @param thisStudyNo
	 * @param thisReqNo
	 * @return
	 */
	String getConfirmFlag(String thisSpecimenCode, int thisTestItem, String string,
			String thisStudyNo, String thisReqNo);
	/**获取   testData  值
	 * @param thisSpecimenCode
	 * @param thisTestItem
	 * @param string
	 * @param thisStudyNo
	 * @param thisReqNo
	 * @return
	 */
	String getTestData(String thisSpecimenCode, int thisTestItem, String string,
			String thisStudyNo, String thisReqNo);
	
	/**查询 studyNo,reqNo ,userName 列表
	 * @param list			临检数据 id列表
	 * @return
	 */
	List<Map<String,Object>> getStudyNoReqNoUserNameByIds(List<String> list);

	/**查询对应课题、申请、检测项目、检验编号、指标    是否存在已签字且有效的数据，
	 * map中存放  exist：true false ,    msg:提示信息   dataId:待签字数据主键 
	 * @param list
	 * @return
	 */
	Map<String,Object> isHaveValidData(List<String> list);

	/**查询RET#在串口传输时出现的999.9问题的数据Id列表，且是未被修改的
	 * @param retDataIdList
	 * @return
	 */
	List<String> getNoeditDataIdList(List<String> retDataIdList);

	/**查询实体列表
	 * @param noEditRetDataIdList
	 * @return
	 */
	List<TblClinicalTestData> getByIdList(List<String> dataIdList);

	/**编辑临检数据，并保存修改痕迹
	 * @param tblTraceList
	 */
	void editSave(List<TblTrace> tblTraceList);
	
}
