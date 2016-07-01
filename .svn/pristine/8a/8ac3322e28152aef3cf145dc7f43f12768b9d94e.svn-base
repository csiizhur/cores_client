package com.lanen.service.path;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.lanen.base.BaseDao;
import com.lanen.model.path.TblHistopathCheckContentIndex;

/**镜检内容组合service
 * @author Administrator
 *
 */
public interface TblHistopathCheckContentService extends BaseDao<TblHistopathCheckContentIndex> {

	/**查询解剖计划 dissectNum,animalNum,beginDate,endDate
	 * @param studyNo
	 * @return
	 */
	List<Map<String, Object>> getDissectPlanListByStudyNo(String studyNo);

	/**查询解剖计划动物列表 dissectNum,animalCode,gender,dosageDesc,anatomyDate
	 * @param studyNo
	 * @return
	 */
	List<Map<String, Object>> getDissectPlanAnimalMapListByStudyNo(String studyNo);

	/**查询组织取材批次 batchId,batchSn,animalNum,createTime,sliceType
	 * @param studyNo
	 * @return
	 */
	List<Map<String, Object>> getBatchIndexListByStudyNo(String studyNo);

	/**查询 该专题下，所有批次动物  ， batchId,animalCode,gender,dosageDesc,anatomyDate
	 * @param studyNo
	 * @return
	 */
	List<Map<String, Object>> getBatchAnimalMapListByStudyNo(String studyNo);

	/**获得切片编号列表，tissueSliceSnId,sliceCode,visceraOrTissueName,codeDate,animalCode,sliceType
	 * @param studyNo
	 * @return
	 */
	List<Map<String, Object>> getSliceCodeByStudyNo(String studyNo);

	/**查询组合索引列表，  contentIndexId,contentName
	 * @param studyNo
	 * @return
	 */
	List<Map<String, Object>> getContentIndexMapList(String studyNo);

	/**保存数据，
	 * @param contentIndex 无id，createDate
	 * @param animalCodeList
	 * @param tissueSliceSnIdList
	 * @param sliceCodeList
	 * @return
	 */
	TblHistopathCheckContentIndex saveOne(TblHistopathCheckContentIndex contentIndex,
			List<String> animalCodeList, List<String> tissueSliceSnIdList,
			List<String> sliceCodeList);

	/**删除，索引表、动物、切片
	 * @param contentIndexId
	 */
	void deleteOne(String contentIndexId);

	/**查询动物列表，animalCode,gender,dosageDesc,anatomyDate
	 * @param contentIndexId
	 * @return
	 */
	List<Map<String, Object>> getAnimalMapList(String contentIndexId);

	/**查询切片编号列表
	 * @param contentIndexId
	 * @return
	 */
	List<String> getSliceCodeList(String contentIndexId);

	/**查询切片编号列表
	 * @param batchIdSet
	 * @return
	 */
	List<String> getSliceCodeListByBatchIdSet(Set<String> batchIdSet);

}
