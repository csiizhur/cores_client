package com.lanen.service.path;

import java.util.List;
import java.util.Map;

import com.lanen.base.BaseDao;
import com.lanen.model.path.TblTissueSliceBatchExcluded;
import com.lanen.model.path.TblTissueSliceBatchIndex;

/**取材service
 * @author Administrator
 *
 */
public interface TblTissueSliceBatchService extends BaseDao<TblTissueSliceBatchIndex>{

	/**查询解剖任务列表，taskId,planAnatomyDate,animalNum,reason(去除补录数据)
	 * @param studyNo
	 * @return
	 */
	List<Map<String, Object>> getAnatomyTaskListByStudyNo(String studyNo);

	/**查询解剖动物列表，taskId,animalCode,gender,dosageDesc,anatomyDate
	 * @param studyNo
	 * @return
	 */
	List<Map<String, Object>> getTaskAnimalMapListByStudyNo(String studyNo);

	/**查询剂量组列表，dosageNum,maleNum,femaleNum
	 * @param studyNo
	 * @return
	 */
	List<Map<String, Object>> getDosageMapListByStudyNo(String studyNo);

	/**查询剂量组动物列表 ，animalCode,gender,dosageNum,dosageDesc,anatomyDate
	 * @param studyNo
	 * @return
	 */
	List<Map<String, Object>> getDosageAnimalMapListByStudyNo(String studyNo);

	/**查询常规编号脏器列表，tissueSliceVisceraId,sliceCode,visceraOrTissueName,visceraCode,visceraName,
	 * 	subVisceraCode,subVisceraName
	 * @param studyNo
	 * @return
	 */
	List<Map<String, Object>> getCGSliceCodeVisceraMapListByStudyNo(String studyNo);


	/**查询非常规编号脏器列表（签字确认过的），tissueSliceVisceraId,sliceCode,visceraOrTissueName,visceraCode,visceraName,
	 * 	subVisceraCode,subVisceraName
	 * @param studyNo
	 * @param animalCodeList
	 * @return
	 */
	List<Map<String, Object>> getFCGSliceCodeVisceraMapList(String studyNo,
			List<String> animalCodeList);

	/**保存批次 索引、动物、脏器或组织、排除，
	 * @param tblTissueSliceBatchIndex 无 id，批序号，时间
	 * @param animalCodeList
	 * @param tissueSliceVisceraIdList
	 * @param batchExcludedList
	 * @return
	 */
	TblTissueSliceBatchIndex saveOne(TblTissueSliceBatchIndex tblTissueSliceBatchIndex,
			List<String> animalCodeList, List<String> tissueSliceVisceraIdList,
			List<TblTissueSliceBatchExcluded> batchExcludedList);
	
	/**查询追加编号脏器列表，tissueSliceVisceraId,sliceCode,visceraOrTissueName,visceraCode,visceraName,
	 * 	subVisceraCode,subVisceraName
	 * @param studyNo
	 * @return
	 */
	List<Map<String, Object>> getZJSliceCodeVisceraMapListByStudyNo(String studyNo);

	/**查询索引列表，batchId,batchSn,createTime,operator,sliceType
	 * @param studyNo
	 * @return
	 */
	List<Map<String, Object>> getBatchIndexMapListByStudyNo(String studyNo);

	/**根据批次索引Id，查询批次动物列表，animalCode,gender,dosageDesc,anatomyDate
	 * @param batchId
	 * @return
	 */
	List<Map<String, Object>> getBatchAnimalMapListByBatchId(String batchId);

	/**根据批次索引Id，查询批次脏器列表，sliceCode,visceraOrTissueName,tissueSliceVisceraId
	 * @param batchId
	 * @return
	 */
	List<Map<String, Object>> getBatchVisceraMapListByBatchId(String batchId);

	/**根据批次索引Id，查询批次排除列表，animalCode,visceraType，visceraCode，visceraName，subVisceraCode，subVisceraName,reason,tissueSliceVisceraId
	 * @param batchId
	 * @return
	 */
	List<Map<String, Object>> getBatchExcludedMapListByBatchId(String batchId);

	/**查询该专题下，这些动物的自溶、缺如、缺失脏器列表 
	* 		animalCode,visceraType,visceraCode,visceraName,subVisceraCode,subVisceraName,reason
	 * @param studyNo
	 * @param animalCodeList
	 * @return
	 */
	List<Map<String, Object>> getMissViscera(String studyNo, List<String> animalCodeList);

	/**保存批次 索引、动物、脏器或组织、排除
	 * @param tblTissueSliceBatchIndex
	 * @param animalCodeList
	 * @param tissueSliceVisceraIdList
	 * @param batchExcludedList
	 * @return
	 */
	TblTissueSliceBatchIndex updateOne(TblTissueSliceBatchIndex tblTissueSliceBatchIndex,
			List<String> animalCodeList, List<String> tissueSliceVisceraIdList,
			List<TblTissueSliceBatchExcluded> batchExcludedList);

	/**签字确认，
	 * @param tblTissueSliceBatchIndex
	 * @param realName
	 * @return
	 */
	TblTissueSliceBatchIndex signTissueSliceBatch(
			TblTissueSliceBatchIndex tblTissueSliceBatchIndex, String realName);

	/**查询待打印数据，"00"+animalCode as animalCode,sliceCode, confirmFlag
	 * @param batchId
	 * @param studyNo
	 * @return
	 */
	List<Map<String, Object>> getPrintMapList(String batchId, String studyNo);

	/**查询打印的备注内容：排除列表[1101 1 肝脏]
	 * @param batchId
	 * @param studyNo
	 * @return
	 */
	List<String> getPrintRemark(String batchId, String studyNo);

	/** 查询 自己及子脏器（visceraType，visceraCode，visceraName，subVisceraCode，subVisceraName）
	 * 1：若是父脏器，再查询其脏器列表，  2：若是子脏器，直接加载自己即可
	 * @param tissueSliceVisceraId
	 * @return
	 */
	List<Map<String, Object>> getMapListByTissueSliceVisceraId(String tissueSliceVisceraId);

	/**查询 自己及子脏器（visceraType，visceraCode，visceraName，subVisceraCode，subVisceraName）
	 * 1：若是父脏器，再查询其子脏器列表，  2：若是子脏器，直接加载自己即可     ，最后还得排除已排除的
	 * @param studyNo
	 * @param animalCode
	 * @param sliceVisceraId2
	 * @return
	 */
	List<Map<String, Object>> getMapListByStudyNoAnimalCodeSliceVisceraId(String studyNo,
			String animalCode, String sliceVisceraId2);

}
