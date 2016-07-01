package com.lanen.service.studyplan;

import java.util.List;

import com.lanen.base.BaseDao;
import com.lanen.model.studyplan.TblAnimal;
import com.lanen.model.studyplan.TblStudyPlan;

public interface TblAnimalService extends BaseDao<TblAnimal> {

	/**
	 * 通过试验计划（课题编号）获取动物信息
	 * @return List<TblAnimal>
	 */
	List<TblAnimal> getByStudyNo(TblStudyPlan tblStudyPlan);
	/**
	 * 获得未死亡的动物
	 * @param tblStudyPlan
	 * @return
	 */
	List<TblAnimal> getNoDieByStudyNo(TblStudyPlan tblStudyPlan);
	
    /**
     * 获得未死亡和今天以后死亡的
     * @param tblStudyPlan
     * @return
     */
	List<TblAnimal> getNoDieANDTodayDieByStudyNo(TblStudyPlan tblStudyPlan);
	
	/**
	 * 获得死了的动物总数
	 * @param tblStudyPlan
	 * @return
	 */
	List<TblAnimal> getNoDieByAnimalSum(TblStudyPlan tblStudyPlan);
	/**
	 * 获取所以的动物列表
	 * @return List<TblAnimal>
	 */
	List<String> getByStudyNo();
	
	/**
	 * 保存多条动物信息
	 * @param animalList
	 */
	void saveAllAnimals(List<TblAnimal> animalList);
	
	/**
	 * 删除多条动物信息
	 * @param animalList
	 */
	void deleteAnimals(List<TblAnimal> animalList);
	
	/**
	 * 更新多条动物信息
	 * @param animalList
	 */
	void updateAnimals(List<TblAnimal> animalList);
	
	/**
	 * 根据试验计划和动物ID查找动物信息
	 * @param tblStudyPlan
	 * @param animalId
	 * @return
	 */
	TblAnimal getByStudyPlanAndAnimalId(TblStudyPlan tblStudyPlan, String animalId);
	
	/**
	 * 根据试验计划和 分页信息
	 * @param tblStudyPlan
	 * @param page
	 * @param rows
	 * @return
	 */
	List<TblAnimal> getByStudyNoWithPageRows(TblStudyPlan tblStudyPlan, int page,
			int rows);
	
	List<TblAnimal> getByStudyNoDiePageRows(TblStudyPlan tblStudyPlan, int page,
			int rows);
	
	/**
	 * 查询试验计划下的动物总数
	 * @param tblStudyPlan
	 * @return
	 */
	Long getTotalByStudyPlan(TblStudyPlan tblStudyPlan);
	/**
	 * 查询该试验计划下的动物编号列表
	 * @param tblStudyPlan
	 * @return
	 */
	List<String> getAnimalCodeByStudyNo(TblStudyPlan tblStudyPlan);
	 
	/**
	 * 查询YYDB里面的动物信息，同时调用存储过程
	 * @param tblStudyPlan
	 * @return
	 */
	String getAnimalsListForStudyNo(TblStudyPlan tblStudyPlan);
	/** 查询实体
	 * @param tblStudyPlan
	 * @param animalIdStr
	 * @return
	 */
	TblAnimal getByStudyPlanAnimalId(TblStudyPlan tblStudyPlan, String animalIdStr);
	/**查询实体
	 * @param tblStudyPlan
	 * @param animalIdStr
	 * @return
	 */
	TblAnimal getByStudyPlanAnimalCode(TblStudyPlan tblStudyPlan, String animalIdStr);
	
	/**根据专题计划和动物id号查询实体（该动物未被解剖），若未查询到则返回null
	 * @param tblStudyPlan
	 * @param animalStr
	 * @return
	 */
	TblAnimal getByStudyPlanAnimalId2(TblStudyPlan tblStudyPlan, String animalId);
	
	/**根据专题计划和动物编号查询实体（该动物未被解剖），若未查询到则返回null
	 * @param tblStudyPlan
	 * @param animalStr
	 * @return
	 */
	TblAnimal getByStudyPlanAnimalCode2(TblStudyPlan tblStudyPlan, String animalCode);
	
}
