package com.lanen.service.studyplan;

import java.util.List;

import com.lanen.base.BaseDao;
import com.lanen.model.studyplan.TblStudyPlan;
import com.lanen.model.studyplan.TblTestIndexPlan;

public interface TblTestIndexPlanService extends BaseDao<TblTestIndexPlan> {

	/**
	 * 根据试验计划获得课题计划检验指标
	 * @param studyPlan
	 * @return
	 */
	List<TblTestIndexPlan> getByStudyNo(TblStudyPlan studyPlan);
	
	/**
	 * 删除多条数据
	 * @param objList
	 */
	void deleteIndexPlans(List<TblTestIndexPlan> objList);
	
	/**
	 * 保存多条数据
	 * @param objList
	 */
	void saveIndexPlans(List<TblTestIndexPlan> objList);
	
	/**
	 * 删除试验计划下的数据
	 * @param studyPlan
	 */
	void deleteByStudyPlan(TblStudyPlan studyPlan);
}
