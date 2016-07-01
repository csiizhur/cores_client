package com.lanen.service.studyplan;

import java.util.Date;
import java.util.List;

import com.lanen.base.BaseDao;
import com.lanen.model.studyplan.TblStudyPlan;

public interface TblStudyPlanService extends BaseDao<TblStudyPlan> {

	/**
	 * 根据年限查询当前试验计划
	 * @param year
	 * @return
	 */
	List<TblStudyPlan> findWithYear(String year);

	/**
	 * 取得全部试验计划
	 * @return
	 */
	public List<TblStudyPlan> getAll();

	/**
	 * 查询所有临时任务的委托单位
	 * @return
	 */
	List<String> findAllTempClient();

	/**获取年份列表
	 * @return
	 */
	List<Object> getYearStrList();

	/**返回当前日期
	 * @return
	 */
	Date getCurrentDate();
	

}
