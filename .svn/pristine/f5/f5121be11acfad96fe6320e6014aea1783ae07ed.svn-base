package com.lanen.service.studyplan;

import java.util.List;
import java.util.Map;


import com.lanen.base.BaseDao;
import com.lanen.model.studyplan.TblDoseSetting;
import com.lanen.model.studyplan.TblStudyPlan;

public interface TblDoseSettingService extends BaseDao<TblDoseSetting> {

	/**
	 *  通过课题编号获取所有
	 * @param String
	 * @return List<TblDoseSetting>
	 */
	List<TblDoseSetting> getByStudyNo(TblStudyPlan tblStudyPlan);
	
	/**
	 * 唯一性检查
	 * @param tblStudyPlan
	 * @param dosageNumPara
	 * @return boolean
	 */
	boolean uniqueCheck(TblStudyPlan tblStudyPlan , int dosageNumPara, String dosId);
	
	/**
	 * 覆写BaseDao方法，保存时自动获得id
	 * @param doseSetting
	 */
	void save(TblDoseSetting doseSetting);
	
	/**
	 * 保存多条记录
	 * @param tblDoseSettings
	 */
	void saveAll(List<TblDoseSetting> tblDoseSettings);
	
	/**
	 * 更新多条记录
	 * @param tblDoseSettings
	 * @param studyPlan
	 */
	void updateAll(List<TblDoseSetting> tblDoseSettings ,TblStudyPlan studyPlan);
	
	/**
	 * 再编辑情况下更新多条记录
	 * @param tblDoseSettings
	 * @param studyPlan
	 */
	void updateAllINApplyRevise(List<TblDoseSetting> tblDoseSettings ,TblStudyPlan studyPlan);
	
	
	
	/**
	 * 查询具体的剂量设置
	 * @param tblStudyPlan
	 * @param dosageNum
	 * @return
	 */
	TblDoseSetting getByStudyNoGroup(TblStudyPlan tblStudyPlan ,int dosageNum );

	/**获得下一个组号
	 * @param studyNoPara
	 * @return
	 */
	int getNextNumByStudyNo(String studyNoPara);

	/**获取mapLIst
	 * @param studyNoPara
	 * @return
	 */
	List<Map<String, Object>> getMapListByStudyNo(String studyNoPara);

	/**删除剂量组，同时把其他剂量组号重新调整下
	 * @param id
	 * @param dosageNum
	 * @param studyNo
	 */
	void delete(String id, int dosageNum, String studyNo);

	/**根据课题编号删除剂量组
	 * @param studyNo
	 */
	void deleteByStudyNo(String studyNo);

	/**生成动物编号
	 * @param tblDoseSettingList
	 * @param tblStudyPlan
	 */
	void createAnimalCodeAll(List<TblDoseSetting> tblDoseSettingList,
			TblStudyPlan tblStudyPlan);
	/**
	 * 更新时保持历史数据
	 * @param tblDoseSetting
	 */
    void  updateAndHis(TblDoseSetting tblDoseSetting);

	/**加载剂量组说明list（去重）
	 * @return
	 */
	List<String> getdosageDescList();

	/**上移
	 * @param studyNoPara
	 * @param dosageNum
	 */
	void upMove(String studyNoPara, int dosageNum);

	/**下移
	 * @param studyNoPara
	 * @param dosageNum
	 */
	void downMove(String studyNoPara, int dosageNum);

	/**获得该专题下的剂量组
	 * @param studyNo
	 * @return
	 */
	List<TblDoseSetting> getListByStudyNo(String studyNo);
	
}
