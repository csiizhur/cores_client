package com.lanen.service.studyplan;

import java.util.Date;
import java.util.List;

import com.lanen.base.BaseDao;
import com.lanen.model.studyplan.TblStudyPlan;

public interface TblStudyPlanService extends BaseDao<TblStudyPlan> {

	/**
	 * 根据年限查询当前启动试验计划
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

	/**
	 * 查询所有试验计划（临时任务除外）
	 * @return
	 */
	List<TblStudyPlan> getAllNoTempTask();
	/**
	 * 查询该年的所有试验计划（临时任务除外）
	 * @param year
	 * @return
	 */
	List<TblStudyPlan> getListNoTempTask(String year);

	/**
	 * 查询所有试验计划编号
	 * @return
	 */
	List<String> getAllStudyNo();

	/**
	 * 查询当前用户的该年的所有试验计划（临时任务除外）
	 * @param year
	 * @return
	 */
	List<TblStudyPlan> getMyListNoTempTask(String year, String realName);
	
	/**
	 * 查询部门的该年的所有试验计划（临时任务除外）
	 * @param year
	 * @return
	 */
	List<TblStudyPlan> getDepartmentListNoTempTask(String year, List<String> list,String name);
	
	/**
	 * 查询当所有的该年的所有试验计划（临时任务除外）
	 * @param year
	 * @return
	 */
	List<TblStudyPlan> getAllListNoTempTask(String year);
	/**
	 * 查询当前用户的该年的所有试验计划（验证、时任务除外）
	 * @param year
	 * @return
	 */
	List<TblStudyPlan> getMyListNoValidationNoTempTask(String year,
			String realName);
	
	/**
	 * 查询部门的该年的所有试验计划（验证、时任务除外）
	 * @param year
	 * @return
	 */
	List<TblStudyPlan> getDepartmentListNoValidationNoTempTask(String year,
			List<String> list,String name);
	
	/**
	 * 查询当前用户的该年的所有试验计划（验证、时任务除外）
	 * @param year
	 * @return
	 */
	List<TblStudyPlan> getAllListNoValidationNoTempTask(String year);

	/**
	 * 更新实体
	 * @param studyNoPara
	 * @param animalCodeMode
	 */
	void update(String studyNoPara, int animalCodeMode);
	
	/**获取年份列表
	 * @return
	 */
	List<Object> getYearStrList();
	
	/**根据课题编号获得专题计划
	 * @param studyNo 
	 * @return
	 */
	TblStudyPlan getByStudyNo(String studyNo);
	
	
	/**根据专题编号查询实体（已提交专题计划），
	 * 若专题编号不存在，则返回null，
	 * 若查询到的实体无动物（无动物种类）或无病理计划则返回null
	 * @param currentStudyNo
	 * @return
	 */
	TblStudyPlan getByStudyNo2(String currentStudyNo);
	

	/**得到该SD的所有专题的对应的年份列表（去重）
	 * @param realName
	 * @return
	 */
	List<String> getYearList(String realName);

	/**所有专题的对应的年份列表（去重）
	 * @return
	 */
	List<String> getYearList();
	
	/**获取专题负责人姓名
	 * @param studyNo
	 * @return
	 */
	String getSDByStudyNo(String studyNo);
	
	/**获取病理专题负责人姓名
	 * @param studyNo
	 * @return
	 */
	String getPathSDByStudyNo(String studyNo);
	/**返回当前日期
	 * @return
	 */
	Date getCurrentDate();
}
